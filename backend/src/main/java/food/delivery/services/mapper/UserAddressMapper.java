package food.delivery.services.mapper;

import food.delivery.entities.UserAddress;
import food.delivery.services.dto.UserAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author szendihorvath
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserAddressMapper extends EntityMapper<UserAddressDTO, UserAddress> {

    @Mapping(target = "user", ignore = true)
    UserAddress toEntity(UserAddressDTO userAddressDTO);

    UserAddressDTO toDto(UserAddress userAddress);

    default UserAddress fromId(Long id) {
        if ( id == null ) {
            return null;
        }
        UserAddress userAddress = new UserAddress();
        userAddress.setId(id);
        return userAddress;
    }

}
