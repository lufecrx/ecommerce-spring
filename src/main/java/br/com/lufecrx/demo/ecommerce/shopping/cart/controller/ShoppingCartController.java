package br.com.lufecrx.demo.ecommerce.shopping.cart.controller;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufecrx.demo.ecommerce.shopping.cart.model.ShoppingCart;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.dto.ShoppingCartDTO;
import br.com.lufecrx.demo.ecommerce.shopping.cart.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The ShoppingCartController class is responsible for handling the HTTP requests related to shopping carts.
 * It uses the ShoppingCartService to perform operations on the database.
 * 
 * @see ShoppingCartService
 * @see ShoppingCart
 */
@RestController
@ApiResponse(responseCode = "403", description = "You are not authorized to access this resource")
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    
    @Autowired
    private ShoppingCartService shoppingCartService;

    private ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * This method returns the shopping cart of the user.
     * @return The shopping cart found.
     */
    @Operation(summary = "Get the shopping cart of the user", description = "Get the shopping cart of the current user and return it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Shopping cart found"),
    })
    @GetMapping("/get")
    public ResponseEntity<ShoppingCartDTO> getCart() {
        ShoppingCartDTO cart = shoppingCartService.getCart();
        return ResponseEntity.ok(cart);
    }

    /**
     * This method adds a product to the shopping cart.
     * @param productId the ID of the product to be added to the cart, passed as a request parameter.
     * @param quantity the quantity of the product to be added to the cart, passed as a request parameter.
     * @return a message indicating that the product was added to the cart.
     */
    @Operation(summary = "Add a product to the shopping cart", description = "Add a product to the shopping cart of the current user given the product ID and quantity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product added to the shopping cart"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/add")
    public ResponseEntity<String> addProductToCart(
        @RequestParam(name = "product") Long productId,
        @RequestParam(name = "quantity") Integer quantity) {
        shoppingCartService.addProductToCart(productId, quantity);
        return ResponseEntity.ok(bundle.getString("cart.successfully_added_product"));
    }

    /**
     * This method removes a product from the shopping cart.
     * @param productId the ID of the product to be removed from the cart, passed as a request parameter.
     * @return a message indicating that the product was removed from the cart.
     */
    @Operation(summary = "Remove a product from the shopping cart", description = "Remove a product from the shopping cart of the current user given the product ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product removed from the shopping cart"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/remove")
    public ResponseEntity<String> removeProductFromCart(
        @RequestParam(name = "product") Long productId) {
        shoppingCartService.removeProductFromCart(productId);
        return ResponseEntity.ok(bundle.getString("cart.successfully_deleted_product"));
    }

    /**
     * This method clears the shopping cart.
     * @return a message indicating that the cart was cleared.
     */
    @Operation(summary = "Clear the shopping cart", description = "Clear the shopping cart of the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Shopping cart cleared"),
    })
    @PutMapping("/clear")
    public ResponseEntity<String> clearCart() {
        shoppingCartService.clearCart();
        return ResponseEntity.ok(bundle.getString("cart.successfully_cleared"));
    }

}
