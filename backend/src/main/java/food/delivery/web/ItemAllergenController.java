package food.delivery.web;

import food.delivery.repositories.ItemAllergenRepository;
import food.delivery.services.mapper.ItemAllergenMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author szendihorvath
 */
@Slf4j
@RestController
@RequestMapping("/item-allergens")
public class ItemAllergenController extends BaseController {

    private final ItemAllergenRepository itemAllergenRepository;
    private final ItemAllergenMapper itemAllergenMapper;

    public ItemAllergenController(
            ItemAllergenRepository itemAllergenRepository,
            ItemAllergenMapper itemAllergenMapper
    ) {
        this.itemAllergenRepository = itemAllergenRepository;
        this.itemAllergenMapper = itemAllergenMapper;
    }
}
