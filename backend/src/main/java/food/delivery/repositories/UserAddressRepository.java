package food.delivery.repositories;

import food.delivery.entities.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author szendihorvathjozsef
 */
@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
