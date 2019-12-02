package food.delivery.services;

import food.delivery.entities.Order;
import food.delivery.entities.User;
import food.delivery.repositories.OrderItemRepository;
import food.delivery.repositories.OrderRepository;
import food.delivery.services.dto.CouponDTO;
import food.delivery.services.dto.OrderDTO;
import food.delivery.services.mapper.OrderItemMapper;
import food.delivery.services.mapper.OrderMapper;
import food.delivery.util.enums.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * @author szendihorvath
 */
@Service
@Slf4j
public class OrderService {

    private final OrderMapper orderMapper;
    private final UserService userService;
    private final CouponService couponService;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;
    private final EntityManager entityManager;

    public OrderService(
            OrderMapper orderMapper,
            UserService userService,
            CouponService couponService,
            OrderRepository orderRepository,
            OrderItemMapper orderItemMapper,
            OrderItemRepository orderItemRepository,
            EntityManager entityManager) {
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.couponService = couponService;
        this.orderRepository = orderRepository;
        this.orderItemMapper = orderItemMapper;
        this.orderItemRepository = orderItemRepository;
        this.entityManager = entityManager;
    }

    public Optional<Order> createNewOrder(OrderDTO orderDTO, List<CouponDTO> couponDTOS) {
        Order order = orderMapper.toEntity(orderDTO);
        order.setStatus(OrderStatus.ORDERED);

        if ( orderDTO.getUser() != null && orderDTO.getUser().getId() == null ) {
            User user = userService.createAnonymousUser(orderDTO.getUser());
            order.setUser(user);
        }
        if (!CollectionUtils.isEmpty(couponDTOS)) {
            couponDTOS.forEach(coupon -> couponService.useCoupon(coupon.getId()));
        }

        order.getOrders().forEach(orderItem -> orderItem.setOrder(order));
        orderRepository.saveAndFlush(order);
        entityManager.refresh(order);
        return Optional.of(order);
    }
}
