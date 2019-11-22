package food.delivery.services.mapper;

import food.delivery.entities.User;
import food.delivery.services.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author szendihorvath
 */
//@Mapper(componentModel = "spring", uses = {UserAddressMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    /*@Mapping(target = "authorities", ignore = true)
    UserDTO toDto(User user);

    default User fromId(Long id) {
        if ( id == null ) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }*/
}
