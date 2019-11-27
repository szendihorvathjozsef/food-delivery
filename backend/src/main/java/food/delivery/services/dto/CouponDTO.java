package food.delivery.services.dto;

import lombok.Data;

/**
 * @author szendihorvath
 */
@Data
public class CouponDTO {

    private Long id;

    private String name;

    private Integer percent;

    private String itemType;

    private UserDTO user;

}
