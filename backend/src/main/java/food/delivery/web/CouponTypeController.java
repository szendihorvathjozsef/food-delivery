package food.delivery.web;

import food.delivery.entities.CouponType;
import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.repositories.CouponTypeRepository;
import food.delivery.services.CouponService;
import food.delivery.services.dto.CouponTypeDTO;
import food.delivery.services.mapper.CouponTypeMapper;
import food.delivery.util.HeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author szend
 */
@Slf4j
@RestController
@RequestMapping("/coupon-types")
public class CouponTypeController extends BaseController {

    private static final String ENTITY_NAME = "couponType";

    private final CouponTypeMapper couponTypeMapper;
    private final CouponTypeRepository couponTypeRepository;
    private final CouponService couponService;

    public CouponTypeController(CouponTypeMapper couponTypeMapper, CouponTypeRepository couponTypeRepository, CouponService couponService) {
        this.couponTypeMapper = couponTypeMapper;
        this.couponTypeRepository = couponTypeRepository;
        this.couponService = couponService;
    }

    @GetMapping
    public ResponseEntity<List<CouponTypeDTO>> list() {
        log.debug("REST request to list Coupon types");
        List<CouponType> coupons = couponTypeRepository.findAll();
        return new ResponseEntity<>(couponTypeMapper.toDto(coupons), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CouponTypeDTO> create(@RequestBody CouponTypeDTO couponTypeDTO) throws URISyntaxException {
        log.debug("REST request to save Coupon type: {}", couponTypeDTO);

        if ( couponTypeDTO.getId() != null ) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }

        CouponType couponType = couponTypeMapper.toEntity(couponTypeDTO);
        couponType = couponService.createCouponType(couponType);
        CouponTypeDTO result = couponTypeMapper.toDto(couponType);
        return ResponseEntity.created(new URI("/orders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Coupon : {}", id);
        couponTypeRepository.deleteById(id);
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
