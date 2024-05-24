package br.com.lufecrx.demo.ecommerce.shopping.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufecrx.demo.ecommerce.shopping.cart.model.CartItem;

/**
 * CartItemRepository is an interface that extends the JpaRepository interface.
 * It provides methods to perform CRUD operations on CartItem entities.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
}
