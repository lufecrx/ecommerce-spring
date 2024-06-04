package br.com.lufecrx.demo.ecommerce.exception.auth.domain.user;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the user is not enabled.
 * Reference for the error message in the messages.properties file: user.not_enabled
 * 
 */
@Slf4j
public class UserNotEnabledException extends RuntimeException{
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the UserNotEnabledException class.
     * It logs the error message.
     */
    public UserNotEnabledException() {
        super(bundle.getString("user.not_enabled"));
        log.error(bundle.getString("user.not_enabled"));
    }
    
    /**
     * Constructor for the UserNotEnabledException class.
     * It logs the error message and the cause of the exception.
     * 
     * @param cause The cause of the exception
     */
    public UserNotEnabledException(Throwable cause) {
        super(bundle.getString("user.not_enabled"), cause);
        log.error(bundle.getString("user.not_enabled"), cause);
    }
}
