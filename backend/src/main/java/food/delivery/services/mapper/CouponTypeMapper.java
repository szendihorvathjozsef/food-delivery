package food.delivery.services.mapper;

import food.delivery.entities.CouponType;
import food.delivery.services.dto.CouponTypeDTO;
import org.mapstruct.Mapper;

/**
 * @author szend
 */
@Mapper(componentModel = "spring")
public interface CouponTypeMapper extends EntityMapper<CouponTypeDTO, CouponType> {

    CouponType toEntity(CouponTypeDTO dto) ;

    CouponTypeDTO toDto(CouponType entity);

}
