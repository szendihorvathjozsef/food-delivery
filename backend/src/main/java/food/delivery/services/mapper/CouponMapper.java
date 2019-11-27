package food.delivery.services.mapper;

import food.delivery.entities.Coupon;
import food.delivery.services.dto.CouponDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

/**
 * @author szendihorvath
 */
@Mapper(componentModel = "spring")
public interface CouponMapper extends EntityMapper<CouponDTO, Coupon> {

    @Mapping(target = "user", ignore = true)
    Coupon toEntity(CouponDTO couponDTO);

    @Mapping(target = "user", ignore = true)
    CouponDTO toDto(Coupon coupon);

    Set<Coupon> toEntity(Set<CouponDTO> couponDTOS);

    Set<CouponDTO> toDto(Set<Coupon> coupons);
}
