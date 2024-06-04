package br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the cart item is not found.
 * Reference for the error message in the messages.properties file: cart.not_found
 * 
 */
@Slf4j
public class CartItemNotFoundException extends RuntimeException {
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the CartItemNotFoundException class.
     * It logs the error message.
     */
    public CartItemNotFoundException() {
        super(bundle.getString("cart.not_found"));
        log.error(bundle.getString("cart.not_found"));
    }
    
    /**
     * Constructor for the CartItemNotFoundException class.
     * It logs the error message and the cause of the exception.
     * 
     * @param cause The cause of the exception
     */
    public CartItemNotFoundException(Throwable cause) {
        super(bundle.getString("cart.not_found"), cause);
        log.error(bundle.getString("cart.not_found"), cause);
    }
}
