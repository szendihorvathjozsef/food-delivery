package food.delivery.web;

import food.delivery.entities.Coupon;
import food.delivery.exceptions.AccountResourceException;
import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.repositories.CouponRepository;
import food.delivery.security.SecurityUtils;
import food.delivery.services.CouponService;
import food.delivery.services.dto.CouponDTO;
import food.delivery.services.mapper.CouponMapper;
import food.delivery.util.HeaderUtil;
import food.delivery.util.ResponseUtil;
import food.delivery.util.enums.CouponStatus;
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
    private final CouponService couponService;

    public CouponController(CouponMapper couponMapper, CouponRepository couponRepository, CouponService couponService) {
        this.couponMapper = couponMapper;
        this.couponRepository = couponRepository;
        this.couponService = couponService;
    }

    @GetMapping
    public ResponseEntity<List<CouponDTO>> list() {
        log.debug("REST request to list Coupons");
        List<Coupon> coupons = couponRepository.findAll();
        return new ResponseEntity<>(couponMapper.toDto(coupons), HttpStatus.OK);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<CouponDTO>> listUnusedByUser() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        log.debug("REST request to list unused Coupons by User: {}", userLogin);
        List<Coupon> coupons = couponRepository.findAllByUser_LoginAndStatus(userLogin, CouponStatus.UNUSED);
        return new ResponseEntity<>(couponMapper.toDto(coupons), HttpStatus.OK);
    }

    /*@GetMapping("/active")
    public ResponseEntity<List<CouponDTO>> listActiveByUser() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        log.debug("REST request to list unused Coupons by User: {}", userLogin);
        List<Coupon> coupons = couponRepository.findAllByUser_LoginAndStatus(userLogin, CouponStatus.ACTIVE);
        return new ResponseEntity<>(couponMapper.toDto(coupons), HttpStatus.OK);
    }*/

    @GetMapping("/used")
    public ResponseEntity<List<CouponDTO>> listUsedByUsER() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        log.debug("REST request to list used Coupons by User: {}", userLogin);
        List<Coupon> coupons = couponRepository.findAllByUser_LoginAndStatus(userLogin, CouponStatus.USED);
        return new ResponseEntity<>(couponMapper.toDto(coupons), HttpStatus.OK);
    }

    /*@GetMapping("/activate/{id}")
    public ResponseEntity<CouponDTO> activate(@PathVariable Long id) {
        log.debug("REST request to activate Coupon: {}", id);

        if ( id == null ) {
            throw new BadRequestAlertException("Invalid id", "", "");
        }
        return ResponseUtil.wrapOrNotFound(
                couponService.activateCoupon(id),
                HeaderUtil.createAlert(applicationName, "couponManagement.updated", id.toString())
        );
    }*/

    @GetMapping("/use/{id}")
    public ResponseEntity<CouponDTO> use(@PathVariable Long id) {
        log.debug("REST request to use Coupon: {}", id);

        if ( id == null ) {
            throw new BadRequestAlertException("Invalid id", "", "");
        }
        return ResponseUtil.wrapOrNotFound(
                couponService.useCoupon(id),
                HeaderUtil.createAlert(applicationName, "couponManagement.updated", id.toString())
        );
    }

    @PostMapping
    public ResponseEntity<CouponDTO> create(@RequestBody CouponDTO couponDTO) throws URISyntaxException {
        log.debug("REST request to save Coupon: {}", couponDTO);

        if ( couponDTO.getId() != null ) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }

        couponDTO.setStatus(CouponStatus.UNUSED);
        return saveCouponAndGetResponse(couponDTO);
    }

    @PutMapping
    public ResponseEntity<CouponDTO> update(@RequestBody CouponDTO couponDTO) throws URISyntaxException {
        log.debug("REST request to update Coupon: {}", couponDTO);

        if ( couponDTO.getId() == null ) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idexists");
        }

        return saveCouponAndGetResponse(couponDTO);
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

    private ResponseEntity<CouponDTO> saveCouponAndGetResponse(@RequestBody CouponDTO couponDTO) throws URISyntaxException {
        Coupon coupon = couponMapper.toEntity(couponDTO);
        coupon = couponRepository.save(coupon);
        CouponDTO result = couponMapper.toDto(coupon);
        return ResponseEntity.created(new URI("/coupons/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }
}
