package food.delivery.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserBannedException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public UserBannedException(String message) {
        super(message);
    }

    public UserBannedException(String message, Throwable t) {
        super(message, t);
    }

}
