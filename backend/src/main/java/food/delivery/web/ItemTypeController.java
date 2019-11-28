package food.delivery.web;

import food.delivery.entities.ItemType;
import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.repositories.ItemTypeRepository;
import food.delivery.services.dto.ItemTypeDTO;
import food.delivery.services.mapper.ItemMapper;
import food.delivery.util.HeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author szendihorvathjozsef
 */
@Slf4j
@RestController
@RequestMapping("/item-types")
public class ItemTypeController extends BaseController {

    private static final String ENTITY_NAME = "itemType";

    private final ItemMapper itemMapper;
    private final ItemTypeRepository itemTypeRepository;

    public ItemTypeController(ItemMapper itemMapper, ItemTypeRepository itemTypeRepository) {
        this.itemMapper = itemMapper;
        this.itemTypeRepository = itemTypeRepository;
    }

    @GetMapping
    public ResponseEntity<List<String>> list() {
        log.debug("REST request to list Item Types");
        List<ItemType> itemTypes = itemTypeRepository.findAll();

        if (CollectionUtils.isEmpty(itemTypes)) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(itemTypes.stream().map(ItemType::getName).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemTypeDTO> create(@RequestBody String newType) {
        log.debug("REST request to create Item Type: {}", newType);

        if ( newType == null ) {
            throw new BadRequestAlertException("No type found", ENTITY_NAME, "namenotfound");
        }

        ItemType itemType = new ItemType();
        itemType.setName(newType);

        itemType = itemTypeRepository.save(itemType);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        applicationName,
                        true,
                        ENTITY_NAME,
                        itemType.getName()
                ))
                .body(new ItemTypeDTO(itemType.getName()));
    }
}
