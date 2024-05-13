package br.com.lufecrx.demo.ecommerce.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Category represents a group of products.
 * Each category has an id and a name.
 * 
 * @see Product
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@Builder
public class Category {

    /*
     * The id of the category. It is generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * The name of the category. It cannot be blank.
     */
    @NotBlank(message = "Category name cannot be blank")
    private String name;
    
}
