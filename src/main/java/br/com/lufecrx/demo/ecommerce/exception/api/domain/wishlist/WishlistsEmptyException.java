package br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the wishlist list is empty.
 * Reference for the error message in the messages.properties file: wishlist.empty_list
 * 
 */
@Slf4j
public class WishlistsEmptyException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when the wishlist list is empty.
     * 
     */
    public WishlistsEmptyException() {
        super(bundle.getString("wishlist.empty_list"));
        log.error("Wishlist list is empty.");
    }
}
