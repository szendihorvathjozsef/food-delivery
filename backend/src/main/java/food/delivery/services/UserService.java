package food.delivery.services;

import food.delivery.config.Constants;
import food.delivery.entities.Authority;
import food.delivery.entities.User;
import food.delivery.entities.UserAddress;
import food.delivery.exceptions.EmailAlreadyUsedException;
import food.delivery.exceptions.InvalidPasswordException;
import food.delivery.exceptions.LoginAlreadyUsedException;
import food.delivery.repositories.AuthorityRepository;
import food.delivery.repositories.UserAddressRepository;
import food.delivery.repositories.UserRepository;
import food.delivery.security.SecurityUtils;
import food.delivery.services.dto.UserAddressDTO;
import food.delivery.services.dto.UserDTO;
import food.delivery.services.mapper.UserAddressMapper;
import food.delivery.util.RandomUtil;
import food.delivery.util.enums.Authorities;
import food.delivery.util.enums.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserAddressRepository userAddressRepository;
    private final UserAddressMapper userAddressMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, UserAddressRepository userAddressRepository, UserAddressMapper userAddressMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.userAddressRepository = userAddressRepository;
        this.userAddressMapper = userAddressMapper;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
                .map(user -> {
                    // activate given user for the registration key.
                    user.setStatus(UserStatus.ACTIVE);
                    user.setActivationKey(null);
                    log.debug("Activated user: {}", user);
                    return user;
                });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    return user;
                });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
                .filter(user -> user.getStatus().equals(UserStatus.ACTIVE))
                .map(user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    return user;
                });
    }

    public User registerUser(UserDTO userDTO, String password) {
        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new LoginAlreadyUsedException();
            }
        });
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail().toLowerCase());
        // new user is not active
        newUser.setStatus(UserStatus.NEED_ACTIVATION);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();

        if (userDTO.getAuthorities() != null) { // if new user has role, it gets the selected role at registration
            authorities = userDTO.getAuthorities().stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
        } else { // if user hasn't role, it gets default role user
            authorityRepository.findById(Authorities.USER.name()).ifPresent(authorities::add);
        }
        newUser.setAuthorities(authorities);
        User result = userRepository.save(newUser);
        if (!CollectionUtils.isEmpty(userDTO.getAddresses())) {
            Set<UserAddress> userAddresses = userAddressMapper.toEntity(userDTO.getAddresses());
            userAddresses = userAddresses.stream().peek(userAddress -> userAddress.setUser(result)).collect(Collectors.toSet());
            userAddressRepository.saveAll(userAddresses);
        }
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.getStatus().equals(UserStatus.ACTIVE)) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        setUserData(user, userDTO);
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setStatus(UserStatus.ACTIVE);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    public User createAnonymousUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(RandomStringUtils.random(15, true, true));
        user.setPassword(passwordEncoder.encode(RandomUtil.generatePassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setStatus(UserStatus.ANONYMOUS);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority("ANONYMOUS"));
        user.setAuthorities(authorities);
        final User finalUser = userRepository.save(user);

        if (!CollectionUtils.isEmpty(userDTO.getAddresses())) {
            Set<UserAddress> userAddresses = userAddressMapper.toEntity(userDTO.getAddresses());
            userAddresses = userAddresses.stream().peek(userAddress -> userAddress.setUser(finalUser)).collect(Collectors.toSet());
            userAddressRepository.saveAll(userAddresses);
        }

        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName  last name of user
     * @param email     email id of user
     */
    public void updateUser(String firstName, String lastName, String email, String phoneNumber, Set<UserAddressDTO> addresses) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setPhoneNumber(phoneNumber);

                    if (!CollectionUtils.isEmpty(addresses)) {
                        Set<UserAddress> userAddresses = userAddressMapper.toEntity(addresses)
                                .stream()
                                .peek(adr -> adr.setUser(user))
                                .collect(Collectors.toSet());
                        user.getAddresses().addAll(userAddresses);
                    }
                    if (email != null && !email.equals(user.getEmail())) {
                        user.setEmail(email.toLowerCase());
                    }

                    log.debug("Changed Information for User: {}", user);
                });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
                .findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    setUserData(user, userDTO);
                    user.setStatus(userDTO.getStatus());
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDTO.getAuthorities().stream()
                            .map(authorityRepository::findById)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(managedAuthorities::add);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(UserDTO::new);
    }

    public void deleteUser(String LOGin) {
        userRepository.findOneByLogin(LOGin).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    log.debug("Changed password for User: {}", user);
                });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        log.info("PW asdASD123: {}", passwordEncoder.encode("asdASD123"));
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private void setUserData(User user, UserDTO userDTO) {
        if (user != null && userDTO != null) {
            user.setLogin(userDTO.getLogin().toLowerCase());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail().toLowerCase());
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }
    }
}
