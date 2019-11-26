package food.delivery.services;

import food.delivery.entities.Order;
import food.delivery.repositories.OrderItemRepository;
import food.delivery.repositories.OrderRepository;
import food.delivery.services.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author szendihorvath
 */
@Service
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        return order;
    }
}
