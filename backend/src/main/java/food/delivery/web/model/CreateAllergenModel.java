package food.delivery.web.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author szendihorvathjozsef
 */
@Data
public class CreateAllergenModel {

    @NotNull
    private String allergen;

}
