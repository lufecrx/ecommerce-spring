package br.com.lufecrx.demo.ecommerce.exception.shopping.domain.order.address;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the list of addresses is empty.
 * Reference for the error message in the messages.properties file: address.empty_list
 * 
 */
@Slf4j
public class AddressListEmptyException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the AddressesEmptyException class.
     * It logs the error message.
     */
    public AddressListEmptyException() {
        super(bundle.getString("address.empty_list"));
        log.error(bundle.getString("address.empty_list"));
    }

    /**
     * Constructor for the AddressesEmptyException class.
     * It logs the error message and the cause of the exception.
     * 
     * @param cause The cause of the exception
     */
    public AddressListEmptyException(Throwable cause) {
        super(bundle.getString("address.empty_list"), cause);
        log.error(bundle.getString("address.empty_list"), cause);
    }
}
