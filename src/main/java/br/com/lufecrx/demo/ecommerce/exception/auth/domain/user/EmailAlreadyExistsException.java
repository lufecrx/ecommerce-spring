package br.com.lufecrx.demo.ecommerce.exception.auth.domain.user;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the email already exists.
 * Reference for the error message in the messages.properties file: user.email_already_exists
 */
@Slf4j
public class EmailAlreadyExistsException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the EmailAlreadyExistsException class.
     * @param email The email that already exists
     */
    public EmailAlreadyExistsException(String email) {
        super(bundle.getString("user.email_already_exists").replace("{email}", email));
        log.error("User with email {} already exists", email);
    }
}
