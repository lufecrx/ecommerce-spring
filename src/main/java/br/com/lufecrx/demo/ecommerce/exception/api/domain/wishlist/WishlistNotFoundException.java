package br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the wishlist is not found.
 * Reference for the error message in the messages.properties file: wishlist.not_found and wishlist.not_found_name
 * 
 */
@Slf4j
public class WishlistNotFoundException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when the wishlist is not found.
     * 
     * @param id the id of the wishlist that is not found.
     * 
     */
    public WishlistNotFoundException(Long id) {
        super(bundle.getString("wishlist.not_found").replace("{id}", id.toString()));
        log.error("Wishlist with id {} not found.", id);
    }
    
    /**
     * Constructor for the exception that is thrown when the wishlist is not found.
     * 
     * @param name the name of the wishlist that is not found.
     * 
     */
    public WishlistNotFoundException(String name) {
        super(bundle.getString("wishlist.not_found_name").replace("{name}", name));
        log.error("Wishlist with name {} not found.", name);
    }
}
