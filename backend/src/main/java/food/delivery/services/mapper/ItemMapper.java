package food.delivery.services.mapper;

import food.delivery.entities.Item;
import food.delivery.entities.ItemType;
import food.delivery.services.dto.ItemDTO;
import org.mapstruct.Mapper;

/**
 * @author szendihorvathjozsef
 */
@Mapper(componentModel = "spring", uses = {ItemAllergenMapper.class})
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {

    Item toEntity(ItemDTO itemDTO);

    ItemDTO toDto(Item item);

    String toItemTypeName(ItemType itemType);

    ItemType toItemType(String itemType);

    default Item fromId(Long id) {
        if ( id == null ) {
            return null;
        }

        Item item = new Item();
        item.setId(id);
        return item;
    }

}
