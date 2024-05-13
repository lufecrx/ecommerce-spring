package br.com.lufecrx.demo.ecommerce.exception.api.domain.category;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when a category with the given id is not found.
 * Reference for the error message in the messages.properties file: category.not_found
 * 
 */
@Slf4j
public class CategoryNotFoundException extends RuntimeException {
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when a category with the given id is not found.
     * 
     * @param id the id of the category that is not found.
     * 
     */
    public CategoryNotFoundException(Long id) {
        super(bundle.getString("category.not_found").replace("{id}", id.toString()));
        log.error("Category with id {} not found.", id);
    }
}
