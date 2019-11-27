package food.delivery.services.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author szendihorvath
 */
@Data
public class ItemDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private Float price;

    private Integer kCal;

    private Integer protein;

    private Integer fat;

    private Integer carbs;

    private String imageName;

    private String itemType;

    private Set<String> allergens;
    
}
