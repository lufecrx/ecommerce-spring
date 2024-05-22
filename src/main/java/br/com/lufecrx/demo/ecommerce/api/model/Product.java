package br.com.lufecrx.demo.ecommerce.api.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Product represents an item that can be added to a Wishlist.
 * Each product has an id, a name, a price, and can belong to multiple categories.
 * 
 * @see Wishlist
 * @see Category
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Builder
public class Product {

    /*
     * The id of the product. It is generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * The name of the product. It cannot be blank.
     */
    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    /*
     * The price of the product. It cannot be less than zero.
     */
    @Min(value = 0, message = "Price cannot be less than zero")
    private Double price;

    /*
     * The wishlists that the product belongs to.
     */
    @ManyToMany(mappedBy = "products")
    @Builder.Default
    private Set<Wishlist> wishlists = new HashSet<>();

    /*
     * The categories that the product belongs to.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_categories",
               joinColumns = @JoinColumn(name = "product_id"),
               inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;
}
