package food.delivery.services;

import food.delivery.entities.Order;
import food.delivery.entities.OrderItem;
import food.delivery.entities.User;
import food.delivery.exceptions.AccountResourceException;
import food.delivery.repositories.OrderItemRepository;
import food.delivery.repositories.OrderRepository;
import food.delivery.security.SecurityUtils;
import food.delivery.services.dto.CouponDTO;
import food.delivery.services.dto.OrderDTO;
import food.delivery.services.mapper.OrderItemMapper;
import food.delivery.services.mapper.OrderMapper;
import food.delivery.util.enums.OrderStatus;
import food.delivery.web.AccountController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * @author szendihorvath
 */
@Service
@Transactional
@Slf4j
public class OrderService {

    private final OrderMapper orderMapper;
    private final UserService userService;
    private final CouponService couponService;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    public OrderService(
            OrderMapper orderMapper,
            UserService userService,
            CouponService couponService,
            OrderRepository orderRepository,
            OrderItemMapper orderItemMapper,
            OrderItemRepository orderItemRepository
    ) {
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.couponService = couponService;
        this.orderRepository = orderRepository;
        this.orderItemMapper = orderItemMapper;
        this.orderItemRepository = orderItemRepository;
    }

    public Long createNewOrder(OrderDTO orderDTO, List<CouponDTO> couponDTOS) {
        Order order = orderMapper.toEntity(orderDTO);
        order.setStatus(OrderStatus.ORDERED);

        if ( orderDTO.getUser() != null && orderDTO.getUser().getId() == null ) {
            User user = userService.createAnonymousUser(orderDTO.getUser());
            order.setUser(user);
        }
        if (!CollectionUtils.isEmpty(couponDTOS)) {
            couponDTOS.forEach(coupon -> couponService.useCoupon(coupon.getId()));
        }

        Set<OrderItem> orderItems = orderItemMapper.toEntity(orderDTO.getOrders())
                .stream()
                .peek(orderItem -> orderItem.setOrder(order))
                .collect(Collectors.toSet());

        orderRepository.saveAndFlush(order);
        orderItemRepository.saveAll(orderItems);

        return order.getId();
    }
}
