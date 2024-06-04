package br.com.lufecrx.demo.ecommerce.shopping.cart.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.lufecrx.demo.ecommerce.shopping.cart.model.CartItem;

/**
 * CartItemDTO is a data transfer object that represents a cart item.
 * It contains the product id and the quantity of the product.
 * 
 * @see CartItem
 */
public record CartItemDTO(
    Long productId, 
    Integer quantity) {

    /**
     * Converts a CartItem object to a CartItemDTO object.
     * 
     * @param cartItem the CartItem object to be converted
     * @return a CartItemDTO object
     */
    public static CartItemDTO from(CartItem cartItem) {
        return new CartItemDTO(cartItem.getProduct().getId(), cartItem.getQuantity());
    }

    /**
     * Converts a list of CartItem objects to a list of CartItemDTO objects.
     * 
     * @param cartItems the list of CartItem objects to be converted
     * @return a list of CartItemDTO objects
     */
    public static List<CartItemDTO> from(List<CartItem> cartItems) {
        return cartItems.stream().map(CartItemDTO::from).collect(Collectors.toList());
    }

}
