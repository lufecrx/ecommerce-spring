package br.com.lufecrx.demo.ecommerce.exception.shopping.domain.order.address;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the address is not found.
 * Reference for the error message in the messages.properties file: address.not_found
 * 
 */
@Slf4j
public class AddressNotFoundException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the AddressNotFoundException class.
     * It logs the error message.
     */
    public AddressNotFoundException() {
        super(bundle.getString("address.not_found"));
        log.error(bundle.getString("address.not_found"));
    }

    /**
     * Constructor for the AddressNotFoundException class.
     * It logs the error message and the cause of the exception.
     * 
     * @param cause The cause of the exception
     */
    public AddressNotFoundException(Throwable cause) {
        super(bundle.getString("address.not_found"), cause);
        log.error(bundle.getString("address.not_found"), cause);
    }
}
