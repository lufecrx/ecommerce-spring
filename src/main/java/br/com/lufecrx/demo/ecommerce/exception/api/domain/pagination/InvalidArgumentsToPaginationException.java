package br.com.lufecrx.demo.ecommerce.exception.api.domain.pagination;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * The InvalidArgumentsToPaginationException class is responsible for throwing an exception when the pagination request has invalid arguments.
 * Examples of invalid arguments are negative page numbers and negative sizes.
 * 
 */
@Slf4j
public class InvalidArgumentsToPaginationException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when the pagination request has invalid arguments.
     * 
     */
    public InvalidArgumentsToPaginationException() {
        super(bundle.getString("pagination.invalid_arguments"));
        log.error(bundle.getString("pagination.invalid_arguments"));
    }
}
