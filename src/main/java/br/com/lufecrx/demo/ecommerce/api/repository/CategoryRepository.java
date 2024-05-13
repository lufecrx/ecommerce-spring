package br.com.lufecrx.demo.ecommerce.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufecrx.demo.ecommerce.api.model.Category;

/**
 * This interface is a repository for the Category entity.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Check if a category with the given name exists in the database.
     * 
     * @param name The name of the category.
     */
    Optional<Category> findByName(String name);

    /**
     * Check if a category with the given name exists in the database.
     * 
     * @param name The name of the category.
     */
    boolean existsByName(String name);

}
