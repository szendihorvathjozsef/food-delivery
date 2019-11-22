package food.delivery.services.mapper;

import java.util.List;

/**
 * @author szendihorvath
 */
public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List <D> toDto(List<E> entityList);

}
