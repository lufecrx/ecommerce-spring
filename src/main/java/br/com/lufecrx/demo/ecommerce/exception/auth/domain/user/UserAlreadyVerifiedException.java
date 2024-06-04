package br.com.lufecrx.demo.ecommerce.exception.auth.domain.user;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the user is already verified.
 * Reference for the error message in the messages.properties file: user.already_verified
 * 
 */
@Slf4j
public class UserAlreadyVerifiedException extends RuntimeException{
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the UserAlreadyVerifiedException class.
     * It logs the error message.
     */
    public UserAlreadyVerifiedException() {
        super(bundle.getString("user.already_verified"));
        log.error(bundle.getString("user.already_verified"));
    }
    
    /**
     * Constructor for the UserAlreadyVerifiedException class.
     * @param cause The cause of the exception
     */
    public UserAlreadyVerifiedException(Throwable cause) {
        super(bundle.getString("user.already_verified"), cause);
        log.error(bundle.getString("user.already_verified"), cause);
    }
}
