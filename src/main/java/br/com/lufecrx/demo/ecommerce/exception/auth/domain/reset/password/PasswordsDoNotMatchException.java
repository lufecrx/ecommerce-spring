package br.com.lufecrx.demo.ecommerce.exception.auth.domain.reset.password;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the passwords do not match.
 * Reference for the error message in the messages.properties file: password_reset.must_match
 * 
 */
@Slf4j
public class PasswordsDoNotMatchException extends RuntimeException{
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the PasswordsDoNotMatchException class.
     * It logs the error message.
     */
    public PasswordsDoNotMatchException() {
        super(bundle.getString("password_reset.must_match"));
        log.error(bundle.getString("password_reset.must_match"));
    }
    
    /**
     * Constructor for the PasswordsDoNotMatchException class.
     * It logs the error message and the cause of the exception.
     * 
     * @param cause The cause of the exception
     */
    public PasswordsDoNotMatchException(Throwable cause) {
        super(bundle.getString("password_reset.must_match"), cause);
        log.error(bundle.getString("password_reset.must_match"), cause);
    }
}
