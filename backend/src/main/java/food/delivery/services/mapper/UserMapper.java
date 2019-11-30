package food.delivery.services.mapper;

import food.delivery.entities.Authority;
import food.delivery.entities.User;
import food.delivery.services.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * @author szendihorvath
 */
@Mapper(componentModel = "spring", uses = {UserAddressMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);

    Set<Authority> toEntity(Set<String> authorities);
    Set<String> toDto(Set<Authority> authorities);

    default String toDto(Authority authority) {
        if ( authority == null || authority.getName() == null ) {
            return null;
        }
        return authority.getName();
    }

    default Authority toEntity(String authorityName) {
        if ( authorityName == null ) {
            return null;
        }

        Authority authority = new Authority();
        authority.setName(authorityName);
        return authority;
    }


    default User fromId(Long id) {
        if ( id == null ) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
