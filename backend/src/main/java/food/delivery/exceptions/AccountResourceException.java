package food.delivery.exceptions;

/**
 * @author szend
 */
public class AccountResourceException extends RuntimeException {
    public AccountResourceException(String message) {
        super(message);
    }
}