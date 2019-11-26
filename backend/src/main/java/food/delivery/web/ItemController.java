package food.delivery.web;

import food.delivery.entities.Item;
import food.delivery.repositories.ItemRepository;
import food.delivery.services.dto.ItemDTO;
import food.delivery.services.mapper.ItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author szendihorvathjozsef
 */
@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {

    private static final String ENTITY_NAME = "item";

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public ItemController(ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> list() {
        log.debug("REST request to get all Order");
        List<Item> orders = itemRepository.findAll();
        return new ResponseEntity<>(itemMapper.toDto(orders), HttpStatus.OK);
    }
}
