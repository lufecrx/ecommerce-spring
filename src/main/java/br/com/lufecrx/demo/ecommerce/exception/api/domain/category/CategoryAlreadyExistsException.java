package br.com.lufecrx.demo.ecommerce.exception.api.domain.category;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when a category with the given name already exists.
 * Reference for the error message in the messages.properties file: category.already_exists
 *
 */
@Slf4j
public class CategoryAlreadyExistsException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when a category with the given name already exists.
     * 
     * @param name the name of the category that already exists.
     * 
     */
    public CategoryAlreadyExistsException(String name) {
        super(bundle.getString("category.already_exists").replace("{name}", name));
        log.error("Category with name {} already exists.", name);
    }
}
