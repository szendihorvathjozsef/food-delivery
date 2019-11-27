package food.delivery.web;

import food.delivery.entities.Order;
import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.repositories.OrderRepository;
import food.delivery.services.dto.OrderDTO;
import food.delivery.services.mapper.OrderMapper;
import food.delivery.util.HeaderUtil;
import food.delivery.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Order: {}", id);
        Optional<OrderDTO> orderDTO = orderRepository.findById(id).map(orderMapper::toDto);
        return ResponseUtil.wrapOrNotFound(orderDTO);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderDTO order) throws URISyntaxException {
        log.debug("REST request to save Order: {}", order);

        if ( order.getId() != null ) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Order orderEntity = orderMapper.toEntity(order);
        log.info("DTO: {}", order);
        log.info("Entity: {}", orderEntity);
        orderEntity = orderRepository.save(orderEntity);
        OrderDTO result = orderMapper.toDto(orderEntity);
        return ResponseEntity.created(new URI("/orders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping
    public ResponseEntity<OrderDTO> update(@Valid @RequestBody OrderDTO order) throws URISyntaxException {
        log.debug("REST request to update Item : {}", order);
        if (order.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Order orderEntity = orderMapper.toEntity(order);
        orderEntity = orderRepository.save(orderEntity);
        OrderDTO result = orderMapper.toDto(orderEntity);
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
        log.debug("REST request to delete Item : {}", id);
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
