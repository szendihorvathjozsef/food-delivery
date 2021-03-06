package food.delivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import food.delivery.util.enums.AddressType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author szendihorvathjozsef
 */
@Data
@Entity
@Table(name = "user_address")
@EqualsAndHashCode(exclude = {"user"})
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String address;

    @NotNull
    @Column(name = "post_code", nullable = false)
    private Integer postCode;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("addresses")
    private User user;

}
