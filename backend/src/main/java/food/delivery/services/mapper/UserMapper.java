package food.delivery.services.mapper;

import food.delivery.entities.Authority;
import food.delivery.entities.User;
import food.delivery.services.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

/**
 * @author szendihorvath
 */
@Mapper(componentModel = "spring", uses = {UserAddressMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);

    String toDto(Authority authority);

    Authority toEntity(String name);

    default User fromId(Long id) {
        if ( id == null ) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
