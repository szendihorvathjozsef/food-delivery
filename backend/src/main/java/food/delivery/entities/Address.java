package food.delivery.entities;

import food.delivery.util.AddressType;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author szendihorvathjozsef
 */
@Entity
@Table(name = "address")
public class Address {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String country;

    private String street;

    private Integer postCode;

    private AddressType type;

}
