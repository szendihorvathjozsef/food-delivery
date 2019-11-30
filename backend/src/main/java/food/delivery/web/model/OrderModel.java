package food.delivery.web.model;

import food.delivery.services.dto.CouponDTO;
import food.delivery.services.dto.OrderDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author szend
 */
@Data
public class OrderModel {

    private List<CouponDTO> coupons;

    @NotNull
    private OrderDTO order;

}
