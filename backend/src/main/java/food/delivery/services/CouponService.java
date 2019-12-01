package food.delivery.services;

import food.delivery.entities.Coupon;
import food.delivery.entities.CouponType;
import food.delivery.entities.User;
import food.delivery.repositories.CouponRepository;
import food.delivery.repositories.CouponTypeRepository;
import food.delivery.repositories.UserRepository;
import food.delivery.services.dto.CouponDTO;
import food.delivery.services.mapper.CouponMapper;
import food.delivery.util.enums.CouponStatus;
import food.delivery.util.enums.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
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
    private final CouponTypeRepository couponTypeRepository;
    private final UserRepository userRepository;

    public CouponService(CouponMapper couponMapper, CouponRepository couponRepository, CouponTypeRepository couponTypeRepository, UserRepository userRepository) {
        this.couponMapper = couponMapper;
        this.couponRepository = couponRepository;
        this.couponTypeRepository = couponTypeRepository;
        this.userRepository = userRepository;
    }

    /*public Optional<CouponDTO> activateCoupon(Long id) {
        log.debug("Activate Coupon: {}", id);

        CouponDTO couponDTO = couponMapper.fromId(id);
        return changeCouponStatus(couponDTO, CouponStatus.ACTIVE);
    }*/

    public CouponType createCouponType(CouponType couponType) {

        CouponType newCouponType = couponTypeRepository.save(couponType);

        List<User> users = userRepository.findAllByStatusIs(UserStatus.ACTIVE);

        if (!CollectionUtils.isEmpty(users)) {
            List<Coupon> coupons = new ArrayList<>();
            users.forEach(user -> {
                Coupon coupon = new Coupon();
                coupon.setUser(user);
                coupon.setType(newCouponType);
                coupons.add(coupon);
            });
            couponRepository.saveAll(coupons);
        }

        return newCouponType;
    }

    public Optional<CouponDTO> useCoupon(Long id) {
        log.debug("Activate Coupon: {}", id);
        CouponDTO couponDTO = couponMapper.fromId(id);
        return changeCouponStatus(couponDTO, CouponStatus.USED);
    }

    private Optional<CouponDTO> changeCouponStatus(CouponDTO couponDTO, CouponStatus couponStatus) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(couponDTO.getId());

        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            coupon.setStatus(couponStatus);
            couponRepository.save(coupon);
            return Optional.of(couponMapper.toDto(coupon));
        }
        return Optional.empty();
    }
}
