package br.com.lufecrx.demo.ecommerce.exception.api.pagination;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * The InvalidSortDirectionException class is responsible for throwing an exception when the sort direction is invalid.
 * Examples of invalid sort directions are values different from "asc" and "desc".
 *
 */
@Slf4j
public class InvalidSortDirectionException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the exception that is thrown when the sort direction is invalid.
     * 
     */
    public InvalidSortDirectionException() {
        super(bundle.getString("pagination.invalid_sort_direction"));
        log.error(bundle.getString("pagination.invalid_sort_direction"));
    }
}
