package food.delivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import food.delivery.services.converter.InstantConverter;
import lombok.Data;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * @author szendihorvathjozsef
 */
@Data
@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;


    @Column(name = "start_time")
    @Convert(converter = InstantConverter.class)
    private Instant startTime = Instant.now();

    @Column(name = "end_time")
    @Convert(converter = InstantConverter.class)
    private Instant endTime;

    @JsonIgnore
    @CreatedDate
    @Column(name = "created_on", updatable = false)
    private Instant createdOn;

    @JsonIgnore
    @LastModifiedDate
    @Column(name = "updated_on")
    private Instant updatedOn;

    @ManyToMany
    @JoinTable(
            name = "menu_item",
            joinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "name")}
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Item> items = new HashSet<>();

}
