package food.delivery.services.mapper;

import food.delivery.entities.Coupon;
import food.delivery.services.dto.CouponDTO;
import org.mapstruct.Mapper;

/**
 * @author szendihorvath
 */
@Mapper(componentModel = "spring", uses = { CouponTypeMapper.class, UserMapper.class })
public interface CouponMapper extends EntityMapper<CouponDTO, Coupon> {

    Coupon toEntity(CouponDTO couponDTO);

    CouponDTO toDto(Coupon coupon);

    default CouponDTO fromId(Long id) {
        if ( id == null ) {
            return null;
        }

        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setId(id);
        return couponDTO;
    }

}
