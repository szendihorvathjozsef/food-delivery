package food.delivery.services.dto;

import food.delivery.entities.UserAddress;
import food.delivery.util.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author szendihorvath
 */
@Data
@NoArgsConstructor
public class UserAddressDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String address;

    @NotNull
    private Integer postCode;

    @NotNull
    private AddressType type;

    public UserAddressDTO(UserAddress userAddress) {
        this.id = userAddress.getId();
        this.address = userAddress.getAddress();
        this.postCode = userAddress.getPostCode();
        this.type = userAddress.getType();
    }
}
