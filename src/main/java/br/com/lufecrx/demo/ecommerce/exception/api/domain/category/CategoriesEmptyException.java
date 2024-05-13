package br.com.lufecrx.demo.ecommerce.exception.api.domain.category;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the categories list is empty.
 * Reference for the error message in the messages.properties file: category.empty_list
 * 
 */
@Slf4j
public class CategoriesEmptyException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when the categories list is empty.
     * 
     */
    public CategoriesEmptyException() {
        super(bundle.getString("category.empty_list"));
        log.error("Categories list is empty.");
    }
}
