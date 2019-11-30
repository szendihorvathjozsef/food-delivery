package food.delivery.services;

import food.delivery.entities.Order;
import food.delivery.entities.User;
import food.delivery.repositories.OrderItemRepository;
import food.delivery.repositories.OrderRepository;
import food.delivery.services.dto.CouponDTO;
import food.delivery.services.dto.OrderDTO;
import food.delivery.services.mapper.OrderMapper;
import food.delivery.util.enums.OrderStatus;
import food.delivery.websocket.NewOrderEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.TimeZone;

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
    private final OrderItemRepository orderItemRepository;
    private final NewOrderEventPublisher newOrderEventPublisher;

    public OrderService(
            OrderMapper orderMapper,
            UserService userService,
            CouponService couponService, OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            NewOrderEventPublisher newOrderEventPublisher
    ) {
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.couponService = couponService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.newOrderEventPublisher = newOrderEventPublisher;
    }

    public OrderDTO createNewOrder(OrderDTO orderDTO, List<CouponDTO> couponDTOS) {
        Order orderEntity = orderMapper.toEntity(orderDTO);
        orderEntity.setStatus(OrderStatus.ORDERED);

        if ( orderDTO.getUser().getId() == null ) {
            User user = userService.createAnonymousUser(orderDTO.getUser());
            orderEntity.setUser(user);
        }

        if (!CollectionUtils.isEmpty(couponDTOS)) {
            couponDTOS.forEach(coupon -> couponService.useCoupon(coupon.getId()));
        }

        orderEntity = orderRepository.save(orderEntity);
        OrderDTO result = orderMapper.toDto(orderEntity, TimeZone.getDefault());
        newOrderEventPublisher.publish(result);
        return result;
    }
}
