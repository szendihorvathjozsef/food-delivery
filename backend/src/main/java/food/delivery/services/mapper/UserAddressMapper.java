package food.delivery.services.mapper;

import food.delivery.entities.UserAddress;
import food.delivery.services.dto.UserAddressDTO;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * @author szendihorvath
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserAddressMapper extends EntityMapper<UserAddressDTO, UserAddress> {

    UserAddress toEntity(UserAddressDTO userAddressDTO);

    UserAddressDTO toDto(UserAddress userAddress);

    Set<UserAddress> toEntity(Set<UserAddressDTO> userAddressDTOS);

    Set<UserAddressDTO> toDto(Set<UserAddress> userAddresses);

    default UserAddress fromId(Long id) {
        if ( id == null ) {
            return null;
        }
        UserAddress userAddress = new UserAddress();
        userAddress.setId(id);
        return userAddress;
    }

}
