package br.com.lufecrx.demo.ecommerce.exception.auth.domain.reset.password;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the arguments to reset the password are missing.
 * Reference for the error message in the messages.properties file: password_reset.arguments_missing
 * 
 */
@Slf4j
public class MissingArgumentsToResetPasswordException extends RuntimeException{
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the MissingArgumentsToResetPasswordException class.
     * It logs the error message.
     */
    public MissingArgumentsToResetPasswordException() {
        super(bundle.getString("password_reset.arguments_missing"));
        log.error(bundle.getString("password_reset.arguments_missing"));
    }
    
    /**
     * Constructor for the MissingArgumentsToResetPasswordException class.
     * It logs the error message and the cause of the exception.
     * 
     * @param cause The cause of the exception
     */
    public MissingArgumentsToResetPasswordException(Throwable cause) {
        super(bundle.getString("password_reset.arguments_missing"), cause);
        log.error(bundle.getString("password_reset.arguments_missing"), cause);
    }
}