package food.delivery.services.dto;

import food.delivery.util.AddressType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author szendihorvath
 */
@Data
public class UserAddressDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String address;

    @NotNull
    private Integer postCode;

    @NotNull
    private AddressType addressType;

}
