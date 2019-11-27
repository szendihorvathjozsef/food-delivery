package food.delivery.services.mapper;

import food.delivery.entities.Item;
import food.delivery.entities.ItemType;
import food.delivery.services.dto.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author szendihorvathjozsef
 */
@Mapper(componentModel = "spring", uses = {ItemAllergenMapper.class})
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {

    @Mapping(target = "itemType.name", source = "itemType")
    Item toEntity(ItemDTO itemDTO);

    @Mapping(target = "itemType", source = "itemType.name")
    ItemDTO toDto(Item item);

    default Item fromId(Long id) {
        if ( id == null ) {
            return null;
        }

        Item item = new Item();
        item.setId(id);
        return item;
    }
}
