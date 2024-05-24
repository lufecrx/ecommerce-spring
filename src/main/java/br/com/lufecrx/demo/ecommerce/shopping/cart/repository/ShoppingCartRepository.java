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
     * Finds a shopping cart by the user id.
     */
    Optional<ShoppingCart> findByUserId(Long userId);
}
