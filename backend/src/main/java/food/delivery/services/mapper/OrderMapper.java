package food.delivery.services.mapper;

import food.delivery.entities.Order;
import food.delivery.services.dto.OrderDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

/**
 * @author szendihorvath
 */
@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, UserMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

    Order toEntity(OrderDTO orderDTO, @Context TimeZone timeZone);

    @Mappings({
            @Mapping(target = "startTime", source = "start"),
            @Mapping(target = "endTime", source = "end")
    })
    OrderDTO toDto(Order order, @Context TimeZone timeZone);

    default Order fromId(Long id) {
        if ( id == null ) {
            return null;
        }

        Order order = new Order();
        order.setId(id);
        return order;
    }

    default LocalDateTime fromInstant(Instant instant, @Context TimeZone timeZone) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, timeZone.toZoneId());
    }
}
