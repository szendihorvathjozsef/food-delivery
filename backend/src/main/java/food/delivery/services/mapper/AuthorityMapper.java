package food.delivery.services.mapper;

import food.delivery.entities.Authority;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * @author szendihorvath
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthorityMapper extends EntityMapper<String, Authority> {

    Set<Authority> toEntity(Set<String> authorities);
    Set<String> toDto(Set<Authority> authorities);

    String toDto(Authority authorities);
    Authority toEntity(String authorities);
}
