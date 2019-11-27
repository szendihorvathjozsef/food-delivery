package food.delivery.web;

import food.delivery.entities.Coupon;
import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.repositories.CouponRepository;
import food.delivery.services.dto.CouponDTO;
import food.delivery.services.mapper.CouponMapper;
import food.delivery.util.HeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author szendihorvath
 */
@Slf4j
@RestController
@RequestMapping("/coupons")
public class CouponController extends BaseController {

    private static final String ENTITY_NAME = "coupon";

    private final CouponMapper couponMapper;
    private final CouponRepository couponRepository;

    public CouponController(CouponMapper couponMapper, CouponRepository couponRepository) {
        this.couponMapper = couponMapper;
        this.couponRepository = couponRepository;
    }

    @GetMapping
    public ResponseEntity<List<CouponDTO>> list() {
        log.debug("REST request to list Coupons");
        List<Coupon> coupons = couponRepository.findAll();
        return new ResponseEntity<>(couponMapper.toDto(coupons), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CouponDTO>> listByUser(@PathVariable Long id) {
        log.debug("REST request to list Coupons by User: {}", id);
        if ( id == null ) {
            throw new BadRequestAlertException("Invalid id", "", "");
        }
        List<Coupon> coupons = couponRepository.findAllByUser(id);
        return new ResponseEntity<>(couponMapper.toDto(coupons), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CouponDTO> create(@RequestBody CouponDTO couponDTO) throws URISyntaxException {
        log.debug("REST request to save Coupon: {}", couponDTO);

        if ( couponDTO.getId() != null ) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Coupon coupon = couponMapper.toEntity(couponDTO);
        coupon = couponRepository.save(coupon);
        CouponDTO result = couponMapper.toDto(coupon);
        return ResponseEntity.created(new URI("/orders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping
    public ResponseEntity<CouponDTO> update(@RequestBody CouponDTO couponDTO) throws URISyntaxException {
        log.debug("REST request to update Coupon: {}", couponDTO);

        if ( couponDTO.getId() == null ) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idexists");
        }

        Coupon coupon = couponMapper.toEntity(couponDTO);
        coupon = couponRepository.save(coupon);
        CouponDTO result = couponMapper.toDto(coupon);
        return ResponseEntity.created(new URI("/orders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Coupon : {}", id);
        couponRepository.deleteById(id);
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
