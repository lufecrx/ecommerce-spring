package br.com.lufecrx.demo.ecommerce.api.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Wishlist represents a list of products that a user wants to buy.
 * Each wishlist has an id, a name, and a set of products.
 * 
 * @see Product
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wishlists")
@Builder
public class Wishlist {

    /*
     * The id of the wishlist. It is generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * The name of the wishlist. It cannot be blank.
     */
    @NotBlank(message = "Wishlist name cannot be blank")
    private String name;

    /*
     * The set of products in the wishlist.
     */
    @OneToMany(mappedBy = "wishlist")
    private Set<Product> products;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user_id")
    // private User user;

    /**
     * Adds a product to the wishlist.
     * If the set of products is not initialized, it initializes it before adding
     * the product.
     *
     * @param product the product to add to the wishlist
     */
    public void addToWishlist(Product product) {
        // Initialize the products set if it's null
        if (this.products == null) {
            this.products = new HashSet<>();
        }
        this.products.add(product);
    }

    /**
     * Removes a product from the wishlist.
     * If the set of products is not initialized, it does nothing.
     *
     * @param product the product to remove from the wishlist
     */
    public void removeFromWishlist(Product product) {
        if (this.products != null) {
            this.products.remove(product);
        }
    }
}
