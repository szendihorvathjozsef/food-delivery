package food.delivery.web;

import food.delivery.entities.User;
import food.delivery.exceptions.EmailAlreadyUsedException;
import food.delivery.exceptions.EmailNotFoundException;
import food.delivery.exceptions.InvalidPasswordException;
import food.delivery.repositories.UserRepository;
import food.delivery.security.SecurityUtils;
import food.delivery.services.MailService;
import food.delivery.services.UserService;
import food.delivery.services.dto.UserDTO;
import food.delivery.services.mapper.UserMapper;
import food.delivery.web.model.KeyAndPasswordModel;
import food.delivery.web.model.PasswordChangeModel;
import food.delivery.web.model.RegisterModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;

/**
 * @author szendihorvath
 */
@RestController
@Slf4j
public class AccountController {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final MailService mailService;

    public AccountController(UserMapper userMapper, UserRepository userRepository, UserService userService, MailService mailService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody RegisterModel managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        managedUserVM.setAuthorities(new HashSet<>());
        managedUserVM.getAuthorities().add("USER");
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
    }

    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
                .map(userMapper::toDto)
                .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPhoneNumber(), userDTO.getAddresses());
    }

    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeModel passwordChangeModel) {
        if (!checkPasswordLength(passwordChangeModel.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeModel.getCurrentPassword(), passwordChangeModel.getNewPassword());
    }

    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        mailService.sendPasswordResetMail(
                userService.requestPasswordReset(mail)
                        .orElseThrow(EmailNotFoundException::new)
        );
    }

    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordModel keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
                userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= RegisterModel.PASSWORD_MIN_LENGTH &&
                password.length() <= RegisterModel.PASSWORD_MAX_LENGTH;
    }
}
