package food.delivery.web.websocket;

import food.delivery.entities.Order;
import food.delivery.repositories.OrderRepository;
import food.delivery.services.dto.OrderDTO;
import food.delivery.services.mapper.OrderMapper;
import food.delivery.websocket.NewOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TimeZone;

/**
 * @author szendihorvathjozsef
 */
@Slf4j
@Controller
public class OrdersController implements ApplicationListener<NewOrderEvent> {

    private final SimpMessageSendingOperations messagingTemplate;

    public OrdersController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/topic")
    @SendTo("/topic/incoming")
    public OrderDTO sendNewOrders(@Payload OrderDTO orderDTO) {
        return orderDTO;
    }

    @Override
    public void onApplicationEvent(NewOrderEvent newOrderEvent) {
        messagingTemplate.convertAndSend("/topic/incoming", newOrderEvent.getOrderDTO());
    }
}
