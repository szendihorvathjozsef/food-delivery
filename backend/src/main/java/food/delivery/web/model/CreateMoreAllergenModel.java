package food.delivery.web.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author szendihorvathjozsef
 */
@Data
public class CreateMoreAllergenModel {

    @NotNull
    private List<String> allergens;

}
