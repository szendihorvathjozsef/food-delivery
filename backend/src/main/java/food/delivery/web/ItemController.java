package food.delivery.web;

import food.delivery.entities.Item;
import food.delivery.entities.ItemAllergen;
import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.repositories.ItemAllergenRepository;
import food.delivery.repositories.ItemRepository;
import food.delivery.services.dto.ItemDTO;
import food.delivery.services.mapper.ItemAllergenMapper;
import food.delivery.services.mapper.ItemMapper;
import food.delivery.util.HeaderUtil;
import food.delivery.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author szendihorvathjozsef
 */
@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController extends BaseController {

    private static final String ENTITY_NAME = "item";

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public ItemController(
            ItemMapper itemMapper,
            ItemRepository itemRepository
    ) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> list() {
        log.debug("REST request to get all Order");
        List<Item> orders = itemRepository.findAll();
        return new ResponseEntity<>(itemMapper.toDto(orders), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Item: {}", id);
        Optional<ItemDTO> itemDTO = itemRepository.findById(id).map(itemMapper::toDto);
        return ResponseUtil.wrapOrNotFound(itemDTO);
    }

    @PostMapping
    public ResponseEntity<ItemDTO> create(@Valid @RequestBody ItemDTO itemDTO) throws URISyntaxException {
        log.debug("REST request to save Item: {}", itemDTO);

        if ( itemDTO.getId() != null ) {
            throw new BadRequestAlertException("A new item cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Item item = itemMapper.toEntity(itemDTO);
        item = itemRepository.save(item);
        ItemDTO result = itemMapper.toDto(item);
        return ResponseEntity.created(new URI("/items/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping
    public ResponseEntity<ItemDTO> update(@Valid @RequestBody ItemDTO itemDTO) throws URISyntaxException {
        log.debug("REST request to update Item : {}", itemDTO);
        if (itemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Item item = itemMapper.toEntity(itemDTO);
        item = itemRepository.save(item);
        ItemDTO result = itemMapper.toDto(item);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        applicationName,
                        true,
                        ENTITY_NAME,
                        itemDTO.getId().toString()
                ))
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Item : {}", id);
        itemRepository.deleteById(id);
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
