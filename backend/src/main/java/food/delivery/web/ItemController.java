package food.delivery.web;

import food.delivery.entities.Item;
import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.repositories.ItemRepository;
import food.delivery.services.StorageService;
import food.delivery.services.dto.ItemDTO;
import food.delivery.services.mapper.ItemMapper;
import food.delivery.util.HeaderUtil;
import food.delivery.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
    private final StorageService storageService;

    public ItemController(
            ItemMapper itemMapper,
            ItemRepository itemRepository,
            StorageService storageService) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> list() {
        log.debug("REST request to get all Order");
        List<Item> items = itemRepository.findAll();
        return new ResponseEntity<>(itemMapper.toDto(items), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Item: {}", id);
        Optional<ItemDTO> itemDTO = itemRepository.findById(id).map(itemMapper::toDto);
        return ResponseUtil.wrapOrNotFound(itemDTO);
    }

    @PostMapping
    public ResponseEntity<ItemDTO> create(@Valid @RequestBody ItemDTO item) throws URISyntaxException {
        log.debug("REST request to save Item: {}", item);

        if ( item.getId() != null ) {
            throw new BadRequestAlertException("A new item cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Item itemEntity = itemMapper.toEntity(item);
        itemEntity.setImageName("");
        itemEntity = itemRepository.save(itemEntity);
        ItemDTO result = itemMapper.toDto(itemEntity);
        return ResponseEntity.created(new URI("/items/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping
    public ResponseEntity<ItemDTO> update(@Valid @RequestBody ItemDTO item) throws URISyntaxException {
        log.debug("REST request to update Item : {}", item);
        if (item.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Item itemEntity = itemMapper.toEntity(item);
        itemEntity = itemRepository.save(itemEntity);
        ItemDTO result = itemMapper.toDto(itemEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        applicationName,
                        true,
                        ENTITY_NAME,
                        item.getId().toString()
                ))
                .body(result);
    }

    @PostMapping("/image")
    public void handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("itemName") String itemName) {
        Item item = itemRepository.findByName(itemName);
        if ( item == null ) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idinvalid");
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String newName = MessageFormat.format(
                "{0}-{1}.{2}",
                RandomStringUtils.randomAlphabetic(8),
                LocalDateTime.now().format(formatter),
                extension
        );

        item.setImageName(newName);
        itemRepository.save(item);
        storageService.store(file, newName);
    }

    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
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
