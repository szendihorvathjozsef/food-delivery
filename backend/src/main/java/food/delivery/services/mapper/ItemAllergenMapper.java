package food.delivery.services.mapper;

import food.delivery.entities.ItemAllergen;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * @author szendihorvathjozsef
 */
@Mapper(componentModel = "spring", uses = {})
public interface ItemAllergenMapper extends EntityMapper<String, ItemAllergen> {

    Set<ItemAllergen> toEntity(Set<String> allergens);
    Set<String> toDto(Set<ItemAllergen> allergens);

    default String toDto(ItemAllergen allergen) {
        if ( allergen == null || allergen.getName() == null ) {
            return null;
        }
        return allergen.getName();
    }

    default ItemAllergen toEntity(String allergen) {
        if ( allergen == null ) {
            return null;
        }

        ItemAllergen itemAllergen = new ItemAllergen();
        itemAllergen.setName(allergen);
        return itemAllergen;
    }

}
