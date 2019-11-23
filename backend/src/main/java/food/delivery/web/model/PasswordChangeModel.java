package food.delivery.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author szendihorvath
 */
@Data
@NoArgsConstructor
public class PasswordChangeModel {

    private String currentPassword;
    private String newPassword;

}
