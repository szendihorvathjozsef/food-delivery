package food.delivery.services.dto;

import lombok.Data;

/**
 * @author szendihorvathjozsef
 */
@Data
public class OrderItemDTO {

    private Long id;

    private Integer quantity;

    private OrderDTO order;

    private ItemDTO item;

}
