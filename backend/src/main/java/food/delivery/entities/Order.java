package food.delivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import food.delivery.services.converter.InstantConverter;
import food.delivery.util.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * @author szendihorvathjozsef
 */
@Data
@Entity
@Table(name = "food_order")
@EqualsAndHashCode(exclude = {"orders"})
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_cost")
    private Double totalCost;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.ORDERED;

    @Column(name = "start_time")
    @Convert(converter = InstantConverter.class)
    private Instant start = Instant.now();

    @Column(name = "end_time")
    @Convert(converter = InstantConverter.class)
    private Instant end;

    @JsonIgnore
    @CreatedDate
    @Column(name = "created_on", updatable = false)
    private Instant createdOn = Instant.now();

    @JsonIgnore
    @LastModifiedDate
    @Column(name = "updated_on")
    private Instant updatedOn = Instant.now();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
    @JsonIgnoreProperties("order")
    private Set<OrderItem> orders = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

}
