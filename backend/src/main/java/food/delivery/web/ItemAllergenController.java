package food.delivery.web;

import food.delivery.entities.ItemAllergen;
import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.repositories.ItemAllergenRepository;
import food.delivery.services.mapper.ItemAllergenMapper;
import food.delivery.util.HeaderUtil;
import food.delivery.web.model.AllergenModel;
import food.delivery.web.model.CreateAllergenModel;
import food.delivery.web.model.CreateMoreAllergenModel;
import food.delivery.web.model.DeleteMoreAllergenModel;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<AllergenModel> create(@Valid @RequestBody CreateAllergenModel createAllergenModel) throws URISyntaxException {
        log.debug("REST request to create Allergen: {}", createAllergenModel);

        ItemAllergen itemAllergen = itemAllergenMapper.toEntity(createAllergenModel.getAllergen());
        itemAllergen = itemAllergenRepository.save(itemAllergen);

        AllergenModel allergenModel = new AllergenModel();
        allergenModel.setAllergen(itemAllergen.getName());

        return ResponseEntity.created(new URI("/item-allergens"))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, itemAllergen.getName()))
                .body(allergenModel);
    }

    @PostMapping("/more")
    public ResponseEntity<List<String>> createMore(@Valid @RequestBody CreateMoreAllergenModel createAllergenModel) throws URISyntaxException {
        log.debug("REST request to create Allergen: {}", createAllergenModel);
        List<ItemAllergen> itemAllergen = itemAllergenMapper.toEntity(createAllergenModel.getAllergens());
        itemAllergen = itemAllergenRepository.saveAll(itemAllergen);
        return ResponseEntity.created(new URI("/item-allergens"))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ""))
                .body(itemAllergenMapper.toDto(itemAllergen));
    }

    @DeleteMapping("/more")
    public void deleteMore(@Valid @RequestBody DeleteMoreAllergenModel allergenModel) {
        log.debug("REST request to delete more Allergen: {}", allergenModel);

        List<ItemAllergen> itemAllergens = itemAllergenMapper.toEntity(allergenModel.getAllergens());
        itemAllergenRepository.deleteAll(itemAllergens);
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        log.debug("REST request to delete Allergen: {}", name);
        if ( name == null ) {
            throw new BadRequestAlertException("Invalid name", ENTITY_NAME, "namenull");
        }

        ItemAllergen itemAllergen = new ItemAllergen();
        itemAllergen.setName(name);

        itemAllergenRepository.delete(itemAllergen);
    }

}
