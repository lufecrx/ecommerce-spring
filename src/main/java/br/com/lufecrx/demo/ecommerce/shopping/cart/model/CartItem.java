package br.com.lufecrx.demo.ecommerce.shopping.cart.model;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CartItem is a class that represents an item in the shopping cart.
 * It contains the product that the user has added to the cart and the quantity of the product.
 * 
 * @see ShoppingCart
 * @see Product
*/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_item")
@Builder
public class CartItem {

    /**
     * The unique identifier of the cart item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The product that the user has added to the cart.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    /**
     * The shopping cart that the cart item belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    /**
     * The quantity of the product that the user has added to the cart.
     */
    private Integer quantity;

    /**
     * Get the unit price of the product.
     * @return The price of the product.
     */
    public Double getUnitPrice() {
        return product.getPrice();
    }

    /**
     * Get price of the cart item.
     * @return The price of the cart item. It is calculated by multiplying the price of the product by the quantity of the product.
     */
    public Double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
