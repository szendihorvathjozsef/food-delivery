package food.delivery.web;

import food.delivery.entities.ItemAllergen;
import food.delivery.repositories.ItemAllergenRepository;
import food.delivery.services.mapper.ItemAllergenMapper;
import food.delivery.util.HeaderUtil;
import food.delivery.web.model.AllergenModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author szendihorvath
 */
@Slf4j
@RestController
@RequestMapping("/item-allergens")
public class ItemAllergenController extends BaseController {

    private static final String ENTITY_NAME = "itemAllergen";

    private final ItemAllergenRepository itemAllergenRepository;
    private final ItemAllergenMapper itemAllergenMapper;

    public ItemAllergenController(
            ItemAllergenRepository itemAllergenRepository,
            ItemAllergenMapper itemAllergenMapper
    ) {
        this.itemAllergenRepository = itemAllergenRepository;
        this.itemAllergenMapper = itemAllergenMapper;
    }

    @GetMapping
    public ResponseEntity<List<String>> list() {
        log.debug("REST request to get all Allergen");
        List<ItemAllergen> itemAllergens = itemAllergenRepository.findAll();
        return new ResponseEntity<>(itemAllergenMapper.toDto(itemAllergens), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody AllergenModel allergenModel) throws URISyntaxException {
        log.debug("REST request to create Allergen: {}", allergenModel);

        ItemAllergen itemAllergen = itemAllergenMapper.toEntity(allergenModel.getAllergen());
        itemAllergen = itemAllergenRepository.save(itemAllergen);
        return ResponseEntity.created(new URI("/item-allergens"))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, itemAllergen.getName()))
                .body(itemAllergenMapper.toDto(itemAllergen));
    }

   /* @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody AllergenModel allergenModel) throws URISyntaxException {
        log.debug("REST request to create Allergen: {}", allergenModel);

        ItemAllergen itemAllergen = itemAllergenMapper.toEntity(allergenModel.getAllergen());
        itemAllergen = itemAllergenRepository.save(itemAllergen);
        return ResponseEntity.created(new URI("/item-allergens"))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, itemAllergen.getName()))
                .body(itemAllergenMapper.toDto(itemAllergen));
    }*/
}
