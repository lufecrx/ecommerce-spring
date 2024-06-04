package br.com.lufecrx.demo.ecommerce.shopping.cart.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.repository.ProductRepository;
import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.product.ProductNotFoundException;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.CartItem;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.ShoppingCart;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.dto.ShoppingCartDTO;
import br.com.lufecrx.demo.ecommerce.shopping.cart.repository.CartItemRepository;
import br.com.lufecrx.demo.ecommerce.shopping.cart.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;

/**
 * This service is responsible for handling the shopping cart operations.
 */
@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
   
    @Autowired
    private ProductRepository productRepository;

    /**
     * Get the shopping cart of the user that is adding the product to the cart.
     * If the user does not have a shopping cart, create a new one.
     * 
     * @return The shopping cart of the user.
     */
    private ShoppingCart getOrCreateShoppingCartForUser() {
        // Get the user that is adding the product to the cart
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the shopping cart of the user
       return shoppingCartRepository.findByUserId(currentUser.getId())
        .orElseGet(() -> {
            ShoppingCart shoppingCart = new ShoppingCart(currentUser);
            return shoppingCartRepository.save(shoppingCart);
        });
    }

    /**
     * Add a product to the shopping cart of the user.
     * 
     * @param productId The identifier of the product to add to the cart.
     * @param quantity The quantity of the product to add to the cart.
     * @throws ProductNotFoundException If the product is not available.
     */
    @Transactional
    public void addProductToCart(Long productId, Integer quantity) {
        
        Optional<Product> optProduct = productRepository.findById(productId);

        if (!optProduct.isPresent()) {
            throw new ProductNotFoundException(productId);
        }
        Product product = optProduct.get();

        ShoppingCart shoppingCart = getOrCreateShoppingCartForUser();
        
        // Verify if the product is already in the cart
        Optional<CartItem> existingCartItem = shoppingCart.getCartItems().stream()
            .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
            .findFirst();

        if (existingCartItem.isPresent()) {
            // If the product is already in the cart, update the quantity
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            // If the product is not in the cart, add the product to the cart
            CartItem cartItem = new CartItem(product, quantity);
            cartItem.setShoppingCart(shoppingCart);
            cartItemRepository.save(cartItem);
            shoppingCart.addCartItem(cartItem);
        }

        shoppingCart.setUpdatedAt(LocalDateTime.now());
        shoppingCartRepository.save(shoppingCart);
    }

    /**
     * Remove a product from the shopping cart of the user.
     * 
     * @param productId The identifier of the product to remove from the cart.
     */
    public void removeProductFromCart(Long productId) {

        ShoppingCart shoppingCart = getOrCreateShoppingCartForUser();

        // Get the product to remove from the cart
        Optional<CartItem> existingCartItem = shoppingCart.getCartItems().stream()
            .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
            .findFirst();
    
        if (existingCartItem.isPresent()) {
            // If the product is in the cart, remove the product from the cart
            CartItem cartItem = existingCartItem.get();
            shoppingCart.removeCartItem(cartItem);
            cartItemRepository.delete(cartItem);
            shoppingCart.setUpdatedAt(LocalDateTime.now());
            shoppingCartRepository.save(shoppingCart);
        } else {
            // If the product is not in the cart, throw an exception
            throw new ProductNotFoundException(productId);
        }
    }

    /**
     * Clear the shopping cart of the user.
     */
    public void clearCart() {   
        ShoppingCart shoppingCart = getOrCreateShoppingCartForUser();
        shoppingCart.getCartItems().clear();
        shoppingCart.setUpdatedAt(LocalDateTime.now());
        shoppingCartRepository.save(shoppingCart);
    }

    /**
     * Get the shopping cart dto of the user.
     * 
     * @return The shopping cart dto of the user.
     */
    public ShoppingCartDTO getCart() {
        ShoppingCart shoppingCart = getOrCreateShoppingCartForUser();
        return ShoppingCartDTO.from(shoppingCart);
    }

    /**
     * Checkout the shopping cart of the user.
     * This operation should create an order with the products in the shopping cart.
     * 
     */
    public void checkout() {
        // TODO: Implement the checkout operation
    }
}
