package food.delivery.web;

import food.delivery.entities.Order;
import food.delivery.exceptions.AccountResourceException;
import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.repositories.OrderRepository;
import food.delivery.security.SecurityUtils;
import food.delivery.services.MailService;
import food.delivery.services.OrderService;
import food.delivery.services.dto.CouponDTO;
import food.delivery.services.dto.OrderDTO;
import food.delivery.services.mapper.OrderMapper;
import food.delivery.util.HeaderUtil;
import food.delivery.util.ResponseUtil;
import food.delivery.util.enums.OrderStatus;
import food.delivery.web.model.ChangeOrdersStatus;
import food.delivery.web.model.OrderModel;
import food.delivery.websocket.NewOrderEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * @author szendihorvathjozsef
 */
@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController {

    private static final String ENTITY_NAME = "order";

    private final OrderMapper orderMapper;
    private final MailService mailService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final NewOrderEventPublisher newOrderEventPublisher;

    public OrderController(
            OrderMapper orderMapper,
            MailService mailService,
            OrderService orderService,
            OrderRepository orderRepository,
            NewOrderEventPublisher newOrderEventPublisher) {
        this.orderMapper = orderMapper;
        this.mailService = mailService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.newOrderEventPublisher = newOrderEventPublisher;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> list() {
        log.debug("REST request to get all Order");
        List<Order> orders = orderRepository.findAll();
        return new ResponseEntity<>(orderMapper.toDto(orders, TimeZone.getDefault()), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> listUsers() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        log.debug("REST request to get all Order for User: {}", userLogin);
        List<Order> orders = orderRepository.findAllByUser_Login(userLogin);
        return new ResponseEntity<>(orderMapper.toDto(orders, TimeZone.getDefault()), HttpStatus.OK);
    }

    @GetMapping("/user/in-progress")
    public ResponseEntity<List<OrderDTO>> listUsersInProgress() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        log.debug("REST request to get all in progress Order for User: {}", userLogin);
        List<Order> orders = orderRepository.findAllByUser_LoginAndStatusIsNot(userLogin, OrderStatus.FINISHED);
        return new ResponseEntity<>(orderMapper.toDto(orders, TimeZone.getDefault()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Order: {}", id);
        Optional<OrderDTO> orderDTO = orderRepository.findById(id).map(order -> orderMapper.toDto(order, TimeZone.getDefault()));
        return ResponseUtil.wrapOrNotFound(orderDTO);
    }

    @GetMapping("/in-progress")
    public ResponseEntity<List<OrderDTO>> listInProgress() {
        log.debug("REST request to list work-in-progress Orders");
        List<Order> orders = orderRepository.findAllByStatusIsNot(OrderStatus.FINISHED);
        return new ResponseEntity<>(orderMapper.toDto(orders, TimeZone.getDefault()), HttpStatus.OK);
    }

    @PostMapping("/finished")
    public ResponseEntity<Boolean> finishOrders(@Valid @RequestBody ChangeOrdersStatus orderIds) {
        if ( orderIds == null || CollectionUtils.isEmpty(orderIds.getIds())) {
            throw new BadRequestAlertException("Invalid ids", ENTITY_NAME, "idnull");
        }
        List<Order> orders = orderRepository.findAllById(orderIds.getIds());
        orders = orders.stream().peek(order -> {
            order.setStatus(OrderStatus.FINISHED);
            order.setEnd(Instant.now());
        }).collect(Collectors.toList());
        orderRepository.saveAll(orders);
        return ResponseEntity.ok().body(true);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderModel orderModel) throws URISyntaxException {
        log.debug("REST request to save Order: {}", orderModel.getOrder());

        OrderDTO order = orderModel.getOrder();
        List<CouponDTO> coupons = orderModel.getCoupons();

        if (order.getId() != null) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }

        OrderDTO result = orderService.createNewOrder(order, coupons);
        newOrderEventPublisher.publish(result);
        mailService.sendOrderMail(result.getUser(), result);
        return ResponseEntity.created(new URI("/orders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping
    public ResponseEntity<OrderDTO> update(@Valid @RequestBody OrderDTO order) throws URISyntaxException {
        log.debug("REST request to update Order : {}", order);
        if (order.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Order orderEntity = orderMapper.toEntity(order);
        orderEntity = orderRepository.save(orderEntity);
        OrderDTO result = orderMapper.toDto(orderEntity, TimeZone.getDefault());
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        applicationName,
                        true,
                        ENTITY_NAME,
                        order.getId().toString()
                ))
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Order : {}", id);
        orderRepository.deleteById(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(
                        applicationName,
                        true,
                        ENTITY_NAME,
                        id.toString()
                ))
                .build();
    }
}
