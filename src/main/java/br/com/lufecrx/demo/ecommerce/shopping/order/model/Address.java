package br.com.lufecrx.demo.ecommerce.shopping.order.model;

import br.com.lufecrx.demo.ecommerce.auth.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents an address in the system.
 * An address is associated with an order and represents the address where the
 * products will be delivered.
 * 
 * An address contains the street, number, neighborhood, city, state, and
 * country.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "Invalid postal code format")
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
