package food.delivery.services.mapper;

import food.delivery.entities.OrderItem;
import food.delivery.services.dto.OrderItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

/**
 * @author szendihorvathjozsef
 */
@Mapper(componentModel = "spring", uses = {ItemMapper.class, UserMapper.class})
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, food.delivery.entities.OrderItem> {

    OrderItem toEntity(OrderItemDTO orderItemDTO);

    @Mapping(target = "order", ignore = true)
    OrderItemDTO toDto(OrderItem orderItem);

    Set<OrderItem> toEntity(Set<OrderItemDTO> orderItemDTOS);

    Set<OrderItemDTO> toDto(Set<OrderItem> orderItems);

    default OrderItem fromId(Long id) {
        if ( id == null ) {
            return null;
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        return orderItem;
    }

}
