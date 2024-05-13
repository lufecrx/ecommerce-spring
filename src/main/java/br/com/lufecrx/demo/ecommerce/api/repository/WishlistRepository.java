package br.com.lufecrx.demo.ecommerce.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;

/**
 * This interface is a repository for the Wishlist entity.
 */
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    
    /**
     * Check if a wishlist with the given name exists in the database.
     * 
     * @param name The name of the wishlist.
     */
    boolean existsByName(String name);

    /**
     * Find a wishlist by its name.
     * 
     * @param name The name of the wishlist.
     */
    Optional<Wishlist> findByName(String name);
    
    // boolean existsByNameAndUser(String name, User user);
    
    // boolean existsByIdAndUser(Long id, User user);

    // Optional<Wishlist> findByNameAndUser(String name, User user);

    // Optional<Wishlist> findByIdAndUser(Long id, User user);
}
