package br.com.lufecrx.demo.ecommerce.shopping.cart.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
     * Constructor with the user that owns the shopping cart.
     * @param user The user that owns the shopping cart.
     * @see User
     */
    public ShoppingCart(User user) {
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

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
    @Builder.Default
    private List<CartItem> cartItems = new ArrayList<>();

    /**
     * Get the total price of the shopping cart.
     * @return The total price of the shopping cart.
     */
    public Double getTotalPrice() {
        return cartItems.stream()
            .mapToDouble(cartItem -> cartItem.getTotalPrice())
            .sum();
    }

    /**
     * Add a cart item to the shopping cart.
     * @param cartItem The cart item to be added to the shopping cart.
     */
    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    /**
     * Remove a cart item from the shopping cart.
     * @param cartItem The cart item to be removed from the shopping cart.
     */
    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    /**
     * Add a product to the shopping cart.
     * @param product The product to be added to the shopping cart.
     */
    public void addProduct(Product product, Integer quantity) {
        CartItem cartItem = new CartItem(product, quantity);
        cartItems.add(cartItem);
    }
}
