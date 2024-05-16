package br.com.lufecrx.demo.ecommerce.exception.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.lufecrx.demo.ecommerce.exception.api.domain.pagination.InvalidArgumentsToPaginationException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.pagination.InvalidSortDirectionException;
import br.com.lufecrx.demo.ecommerce.exception.api.handler.PaginationExceptionsHandler;
import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;

public class PaginationExceptionsTest {

    private PaginationExceptionsHandler paginationExceptionsHandler;
    
    private ResourceBundle bundle;

    @BeforeEach
    public void init() {
        paginationExceptionsHandler = new PaginationExceptionsHandler();
        bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    }

    @Test
    public void testInvalidArgumentsToPaginationException() {
        InvalidArgumentsToPaginationException invalidArgumentsToPaginationException = new InvalidArgumentsToPaginationException();
        String expectedMessage = bundle.getString("pagination.invalid_arguments");

        ResponseEntity<RestErrorMessage> responseEntity = paginationExceptionsHandler.handleInvalidArgumentsToPaginationException(invalidArgumentsToPaginationException);

        // Assert that the response status is BAD_REQUEST and the message is the expected one
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test
    public void testInvalidSortDirectionException() {
        InvalidSortDirectionException invalidSortDirectionException = new InvalidSortDirectionException();
        String expectedMessage = bundle.getString("pagination.invalid_sort_direction");

        ResponseEntity<RestErrorMessage> responseEntity = paginationExceptionsHandler.handleInvalidSortDirectionException(invalidSortDirectionException);

        // Assert that the response status is BAD_REQUEST and the message is the expected one
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }
}