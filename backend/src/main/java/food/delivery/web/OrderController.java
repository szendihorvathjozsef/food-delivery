package food.delivery.web;

import food.delivery.entities.Order;
import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.repositories.OrderRepository;
import food.delivery.services.dto.OrderDTO;
import food.delivery.services.mapper.OrderMapper;
import food.delivery.util.HeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author szendihorvathjozsef
 */
@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController {

    private static final String ENTITY_NAME = "order";

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderController(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> list() {
        log.debug("REST request to get all Order");
        List<Order> orders = orderRepository.findAll();
        return new ResponseEntity<>(orderMapper.toDto(orders), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderDTO orderDTO) throws URISyntaxException {
        log.debug("REST request to save Order: {}", orderDTO);

        if ( orderDTO.getId() != null ) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        OrderDTO result = orderMapper.toDto(order);
        return ResponseEntity.created(new URI("/orders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }
}
