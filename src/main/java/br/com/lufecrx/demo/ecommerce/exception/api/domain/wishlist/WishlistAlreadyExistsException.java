package br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the wishlist already exists.
 * Reference for the error message in the messages.properties file: wishlist.already_exists
 * 
 */
@Slf4j
public class WishlistAlreadyExistsException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when the wishlist already exists.
     * 
     * @param name the name of the wishlist that already exists.
     * 
     */
    public WishlistAlreadyExistsException(String name) {
        super(bundle.getString("wishlist.already_exists").replace("{name}", name));
        log.error("Wishlist with name {} already exists.", name);
    }
}
