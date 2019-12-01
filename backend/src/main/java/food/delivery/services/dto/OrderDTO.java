package food.delivery.services.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author szendihorvath
 */
@Data
@ToString
public class OrderDTO {

    private Long id;

    private Double totalCost;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    private Instant createdOn;

    private Instant updatedOn;

    private Set<OrderItemDTO> orders;

    private UserDTO user;

}
