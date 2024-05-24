package br.com.lufecrx.demo.ecommerce.shopping.cart.model;

import java.time.LocalDateTime;
import java.util.List;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.auth.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ShoppingCart is a class that represents the shopping cart of a user.
 * It contains the products that the user has added to the cart.
 * 
 * @see Product
 * @see User
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shopping_cart")
@Builder
public class ShoppingCart {

    /**
     * The unique identifier of the shopping cart.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user that owns the shopping cart.
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * The date and time when the shopping cart was created.
     */
    private LocalDateTime createdAt;

    /**
     * The date and time when the shopping cart was last updated.
     */
    private LocalDateTime updatedAt;

    /**
     * The items that the user has added to the shopping cart.
     */
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    /**
     * Get the total price of the shopping cart.
     * @return The total price of the shopping cart.
     */
    public Double getTotalPrice() {
        return cartItems.stream()
            .mapToDouble(cartItem -> cartItem.getTotalPrice())
            .sum();
    }
}
