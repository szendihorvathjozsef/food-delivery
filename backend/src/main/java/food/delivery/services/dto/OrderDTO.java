package food.delivery.services.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import food.delivery.entities.OrderItem;
import food.delivery.util.enums.OrderStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author szendihorvath
 */
@Data
public class OrderDTO {

    private Long id;

    private Double totalCost;

    @NotNull
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    private Instant createdOn;

    private Instant updatedOn;

    private Set<OrderItem> orders;

    private UserDTO user;

}
