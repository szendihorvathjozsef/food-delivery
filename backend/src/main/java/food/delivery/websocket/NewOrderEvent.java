package food.delivery.websocket;

import food.delivery.services.dto.OrderDTO;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @author szendihorvathjozsef
 */
@Data
public class NewOrderEvent extends ApplicationEvent {

    private OrderDTO orderDTO;

    public NewOrderEvent(Object source, OrderDTO orderDTO) {
        super(source);
        this.orderDTO = orderDTO;
    }
}
