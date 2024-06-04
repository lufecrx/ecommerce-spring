package br.com.lufecrx.demo.ecommerce.shopping.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufecrx.demo.ecommerce.shopping.cart.model.ShoppingCart;

/**
 * ShoppingCartRepository is an interface that extends the JpaRepository interface.
 * It provides methods to perform CRUD operations on ShoppingCart entities.
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    
    /**
     * Finds a shopping cart by id and user.
     * 
     * @param id The identifier of the shopping cart.
     * @param userId The identifier of the user.
     * @return The shopping cart of the user.
     */
    Optional<ShoppingCart> findByIdAndUserId(Long id, Long userId);

    /**
     * Finds a shopping cart by the user id.
     * 
     * @param userId The identifier of the user.
     * @return The shopping cart of the user.
     */
    Optional<ShoppingCart> findByUserId(Long userId);
}
