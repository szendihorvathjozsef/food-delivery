package food.delivery.services.dto;

import food.delivery.util.enums.CouponStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author szendihorvath
 */
@Data
@RequiredArgsConstructor
public class CouponDTO {

    private Long id;

    private CouponStatus status;

    private CouponTypeDTO type;

    private UserDTO user;

}
