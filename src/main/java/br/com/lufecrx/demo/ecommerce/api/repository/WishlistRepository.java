package br.com.lufecrx.demo.ecommerce.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import br.com.lufecrx.demo.ecommerce.auth.model.User;

/**
 * This interface is a repository for the Wishlist entity.
 */
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    
    /**
     * Check if a wishlist with the given name exists in the database.
     * 
     * @param name The name of the wishlist.
     * @return true if the wishlist exists, false otherwise.
     */
    boolean existsByName(String name);

    /**
     * Find a wishlist by its name.
     * 
     * @param name The name of the wishlist.
     * @return An Optional containing the wishlist if it exists, or an empty Optional otherwise.
     */
    Optional<Wishlist> findByName(String name);

    /**
     * Check if a wishlist with the given name exists in the database and belongs to the given user.
     * @param name The name of the wishlist.
     * @param user The user that owns the wishlist.
     * @return true if the wishlist exists, false otherwise.
     */
    boolean existsByNameAndUser(String name, User user);
    
    /**
     * Check if a wishlist with the given ID exists in the database and belongs to the given user.
     * 
     * @param id The ID of the wishlist.
     * @param user The user that owns the wishlist.
     * @return true if the wishlist exists, false otherwise.
     */
    boolean existsByIdAndUser(Long id, User user);

    /**
     * Find a wishlist by its name and the user that owns it.
     * 
     * @param name The name of the wishlist.
     * @param user The user that owns the wishlist.
     * @return An Optional containing the wishlist if it exists, or an empty Optional otherwise.
     */
    Optional<Wishlist> findByNameAndUser(String name, User user);

    /**
     * Find a wishlist by its ID and the user that owns it.
     * 
     * @param id The ID of the wishlist.
     * @param user The user that owns the wishlist.
     * @return An Optional containing the wishlist if it exists, or an empty Optional otherwise.
     */
    Optional<Wishlist> findByIdAndUser(Long id, User user);

    /**
     * Find all wishlists pageable that belong to the given user.
     * 
     * @param user The user that owns the wishlists.
     * @param pageable The pageable object.
     * @return A Page containing the wishlists.
     */
    Page<Wishlist> findAllByUser(User user, Pageable pageable);
}
