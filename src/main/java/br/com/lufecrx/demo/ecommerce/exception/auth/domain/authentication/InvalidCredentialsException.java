package br.com.lufecrx.demo.ecommerce.exception.auth.domain.authentication;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the user credentials are invalid.
 * Reference for the error message in the messages.properties file: auth.invalid_credentials
 * 
 */
@Slf4j
public class InvalidCredentialsException extends RuntimeException{
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the InvalidCredentialsException class.
     * It logs the error message.
     */
    public InvalidCredentialsException() {
        super(bundle.getString("auth.invalid_credentials"));
        log.error(bundle.getString("auth.invalid_credentials"));
    }
    
    /**
     * Constructor for the InvalidCredentialsException class.
     * It logs the error message and the cause of the exception.
     * 
     * @param cause The cause of the exception
     */
    public InvalidCredentialsException(Throwable cause) {
        super(bundle.getString("auth.invalid_credentials"), cause);
        log.error(bundle.getString("auth.invalid_credentials"), cause);
    }
}
