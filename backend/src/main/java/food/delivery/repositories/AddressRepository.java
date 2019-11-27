package food.delivery.repositories;

import food.delivery.entities.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author szendihorvath
 */
@Repository
public interface AddressRepository extends JpaRepository<UserAddress, Long> {
}
