package food.delivery.services;

import food.delivery.repositories.CouponRepository;
import food.delivery.services.dto.CouponDTO;
import food.delivery.services.mapper.CouponMapper;
import food.delivery.util.enums.CouponStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author szend
 */
@Slf4j
@Service
@Transactional
public class CouponService {

    private final CouponMapper couponMapper;
    private final CouponRepository couponRepository;

    public CouponService(CouponMapper couponMapper, CouponRepository couponRepository) {
        this.couponMapper = couponMapper;
        this.couponRepository = couponRepository;
    }

    public Optional<CouponDTO> activateCoupon(Long id) {
        log.debug("Activate Coupon: {}", id);

        CouponDTO couponDTO = couponMapper.fromId(id);
        return changeCouponStatus(couponDTO, CouponStatus.ACTIVE);
    }

    public Optional<CouponDTO> useCoupon(Long id) {
        log.debug("Activate Coupon: {}", id);
        CouponDTO couponDTO = couponMapper.fromId(id);
        return changeCouponStatus(couponDTO, CouponStatus.USED);
    }

    private Optional<CouponDTO> changeCouponStatus(CouponDTO couponDTO, CouponStatus couponStatus) {
        return Optional.of(couponRepository.findById(couponDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(coupon -> {
                    coupon.setStatus(couponStatus);
                    log.debug("Changed information for Coupon: {}", coupon);
                    return coupon;
                }).map(couponMapper::toDto);
    }
}
