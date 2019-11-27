package food.delivery.web.model;

import food.delivery.services.dto.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * @author szendihorvath
 */
@Data
@NoArgsConstructor
public class RegisterModel extends UserDTO {


    public static final int PASSWORD_MIN_LENGTH = 8;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MAX_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

}
