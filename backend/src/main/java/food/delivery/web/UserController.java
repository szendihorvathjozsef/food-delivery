package food.delivery.web;

import food.delivery.entities.User;
import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.exceptions.EmailAlreadyUsedException;
import food.delivery.exceptions.LoginAlreadyUsedException;
import food.delivery.repositories.UserRepository;
import food.delivery.services.UserService;
import food.delivery.services.dto.UserDTO;
import food.delivery.util.HeaderUtil;
import food.delivery.util.PaginationUtil;
import food.delivery.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends BaseController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> list(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        return ResponseUtil.wrapOrNotFound(
                userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new)
        );
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User: {}", userDTO);

        if ( userDTO.getId() != null ) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
        } else if ( userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent() ) {
            throw new LoginAlreadyUsedException();
        } else if ( userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent() ) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            // TODO kivezetni app props-ba a app nevét
            return ResponseEntity.created(new URI("/users/" + newUser.getLogin()))
                    .headers(HeaderUtil.createAlert("easyOrder", "userManagement.created", newUser.getLogin()))
                    .body(newUser);
        }
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User: {}", userDTO);

        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if ( existingUser.isPresent() && ( !existingUser.get().getId().equals(userDTO.getId()) ) ) {
            throw new EmailAlreadyUsedException();
        }

        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if ( existingUser.isPresent() && ( !existingUser.get().getLogin().equals(userDTO.getLogin()) ) ) {
            throw new LoginAlreadyUsedException();
        }

        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

        // TODO kivezetni app props-ba a app nevét
        return ResponseUtil.wrapOrNotFound(
            updatedUser,
            HeaderUtil.createAlert("easyOrder", "userManagement.updated", userDTO.getLogin())
        );
    }

    @GetMapping("/authorities")
    public List<String> listAuthorities() {
        return userService.getAuthorities();
    }

}
