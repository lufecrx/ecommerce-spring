package br.com.lufecrx.demo.ecommerce.shopping.cart.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.lufecrx.demo.ecommerce.shopping.cart.model.ShoppingCart;

/**
 * ShoppingCartDTO is a data transfer object that represents a shopping cart.
 * It contains a list of cart items, the creation date and the last update date.
 * 
 * @see ShoppingCart
 */
public record ShoppingCartDTO(
    List<CartItemDTO> cartItems, 
    LocalDateTime createdAt, 
    LocalDateTime updatedAt) {

    /**
     * Converts a ShoppingCart object to a ShoppingCartDTO object.
     * 
     * @param shoppingCart the ShoppingCart object to be converted
     * @return a ShoppingCartDTO object
     */
    public static ShoppingCartDTO from(ShoppingCart shoppingCart) {
        List<CartItemDTO> cartItemDTOs = shoppingCart.getCartItems() != null ? CartItemDTO.from(shoppingCart.getCartItems()) : new ArrayList<>();
        return new ShoppingCartDTO(cartItemDTOs, shoppingCart.getCreatedAt(), shoppingCart.getUpdatedAt());
    }

}