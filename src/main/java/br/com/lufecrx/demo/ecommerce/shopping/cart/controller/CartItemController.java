package br.com.lufecrx.demo.ecommerce.shopping.cart.controller;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufecrx.demo.ecommerce.shopping.cart.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The CartItemController class is responsible for handling the HTTP requests related to cart items.
 * It uses the CartItemService to perform operations on the database.
 * 
 * @see CartItemService
 */
@RestController
@RequestMapping("/cart-item")
public class CartItemController {
    
    @Autowired
    private CartItemService cartItemService;
    
    private ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * This method updates the quantity of a cart item.
     * @param cartItemId the ID of the cart item to be updated, passed as a request parameter.
     * @param quantity the new quantity of the cart item, passed as a request parameter.
     * @return a message indicating that the cart item was updated.
     */
    @Operation(summary = "Update the quantity of a cart item", description = "Update the quantity of a cart item given the cart item ID and the new quantity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cart item updated"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to access this resource"),
        @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @PutMapping("/update")
    public ResponseEntity<String> updateCartItemQuantity(
        @RequestParam("item") Long cartItemId, 
        @RequestParam("quantity") Integer quantity) {
        
        cartItemService.updateCartItemQuantity(cartItemId, quantity);
        return ResponseEntity.ok(bundle.getString("cart.successfully_updated"));
    }

    /**
     * This method removes a cart item.
     * @param cartItemId the ID of the cart item to be removed, passed as a request parameter.
     * @return a message indicating that the cart item was removed.
     */
    @Operation(summary = "Remove a cart item", description = "Remove a cart item given the cart item ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cart item removed"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to access this resource"),
        @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @PutMapping("/remove")
    public ResponseEntity<String> removeCartItem(
        @RequestParam("item") Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.ok(bundle.getString("cart.successfully_removed"));
    }
}
