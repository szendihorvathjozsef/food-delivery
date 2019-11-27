package food.delivery.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author szendihorvathjozsef
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Long id;

    private Integer quantity;

    private OrderDTO order;

    private ItemDTO item;

}
