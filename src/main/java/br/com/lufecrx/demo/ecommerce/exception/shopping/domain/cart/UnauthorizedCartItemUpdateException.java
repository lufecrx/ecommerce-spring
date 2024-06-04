package br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the cart item update is not allowed.
 * Reference for the error message in the messages.properties file: cart.not_allowed
 */
@Slf4j
public class UnauthorizedCartItemUpdateException extends RuntimeException {
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the UnauthorizedCartItemUpdateException class.
     * It logs the error message.
     */
    public UnauthorizedCartItemUpdateException() {
        super(bundle.getString("cart.not_allowed"));
        log.error(bundle.getString("cart.not_allowed"));
    }
    
    /**
     * Constructor for the UnauthorizedCartItemUpdateException class.
     * It logs the error message and the cause of the exception.
     * 
     * @param cause The cause of the exception
     */
    public UnauthorizedCartItemUpdateException(Throwable cause) {
        super(bundle.getString("cart.not_allowed"), cause);
        log.error(bundle.getString("cart.not_allowed"), cause);
    }
}
