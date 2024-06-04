package br.com.lufecrx.demo.ecommerce.exception.auth.domain.user;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the user is not found.
 * Reference for the error message in the messages.properties file: user.with_id_not_found and user.with_email_not_found
 * 
 */
@Slf4j
public class UserNotFoundException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the UserNotFoundException class that receives the id of the user that was not found.
     * It logs the error message.
     * @param id The id of the user that was not found
     */
    public UserNotFoundException(Long id) {
        super(bundle.getString("user.with_id_not_found").replace("{id}", id.toString()));
        log.error("User with id {} not found", id);
    }

    /**
     * Constructor for the UserNotFoundException class that receives the email of the user that was not found.
     * It logs the error message.
     * @param email The email of the user that was not found
     */
    public UserNotFoundException(String email) {
        super(bundle.getString("user.with_email_not_found").replace("{email}", email));
        log.error("User with email {} not found", email);
    }
}
