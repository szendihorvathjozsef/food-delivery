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

    String toDto(ItemAllergen allergen);
    ItemAllergen toEntity(String allergen);

}
