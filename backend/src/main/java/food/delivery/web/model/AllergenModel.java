package food.delivery.web.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author szendihorvathjozsef
 */
@Data
public class AllergenModel {

    @NotNull
    private String allergen;

}
