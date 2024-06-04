package br.com.lufecrx.demo.ecommerce.shopping.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart.CartItemNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart.UnauthorizedCartItemUpdateException;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.CartItem;
import br.com.lufecrx.demo.ecommerce.shopping.cart.repository.CartItemRepository;
import jakarta.transaction.Transactional;

/**
 * This service is responsible for handling the cart item operations.
 */
@Service
public class CartItemService {
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    /**
     * Get the authenticated user.
     * @return The authenticated user.
     */
    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Update the quantity of a cart item.
     * 
     * @param cartItemId The identifier of the cart item to update.
     * @param quantity The new quantity of the cart item.
     * @throws CartItemNotFoundException If the cart item is not available.
     * @throws UnauthorizedCartItemUpdateException If the user is not allowed to update the cart item.
     */
    @Transactional
    public void updateCartItemQuantity(Long cartItemId, Integer quantity) {
        User currentUser = getAuthenticatedUser();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new CartItemNotFoundException());
        
        // Check if the user is allowed to update the cart item
        if (!cartItem.getShoppingCart().getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedCartItemUpdateException();
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    /**
     * Remove a cart item from the shopping cart.
     * 
     * @param cartItemId The identifier of the cart item to remove.
     * @throws CartItemNotFoundException If the cart item is not available.
     * @throws UnauthorizedCartItemUpdateException If the user is not allowed to delete the cart item.
     */
    @Transactional
    public void deleteCartItem(Long cartItemId) {
        User currentUser = getAuthenticatedUser();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new CartItemNotFoundException());
        
        // Check if the user is allowed to delete the cart item
        if (!cartItem.getShoppingCart().getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedCartItemUpdateException();
        }

        cartItemRepository.delete(cartItem);
    }
}
