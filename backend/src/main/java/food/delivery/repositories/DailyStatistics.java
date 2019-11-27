package food.delivery.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author szendihorvathjozsef
 */
@Data
@AllArgsConstructor
public class DailyStatistics {
    private Long quantity;
    private String name;
}
