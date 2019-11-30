package food.delivery.websocket;

import food.delivery.services.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author szendihorvathjozsef
 */
@Component
public class NewOrderEventPublisher {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void publish(OrderDTO orderDTO) {
        NewOrderEvent newOrderEvent = new NewOrderEvent(this, orderDTO);
        publisher.publishEvent(newOrderEvent);
    }

}
