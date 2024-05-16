package br.com.lufecrx.demo.ecommerce.exception.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoriesEmptyException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoryAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoryNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.api.handler.CategoryExceptionsHandler;
import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;

public class CategoryExceptionsTest {

    private CategoryExceptionsHandler categoryExceptionsHandler;
    
    private ResourceBundle bundle;

    private Faker faker;

    @BeforeEach
    public void init() {
        faker = new Faker();
        categoryExceptionsHandler = new CategoryExceptionsHandler();
        bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    }

    @Test
    public void testCategoriesEmptyException() {
        CategoriesEmptyException categoriesEmptyException = new CategoriesEmptyException();
        String expectedMessage = bundle.getString("category.empty_list");
        
        ResponseEntity<RestErrorMessage> responseEntity = categoryExceptionsHandler.handleCategoriesEmptyException(categoriesEmptyException);
        
        // Assert that the response status is NOT_FOUND and the message is the expected one
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test
    public void testCategoryAlreadyExistsException() {
        String fakerCategory = faker.commerce().department();
        CategoryAlreadyExistsException categoryAlreadyExistsException = new CategoryAlreadyExistsException(fakerCategory);
        String expectedMessage = bundle.getString("category.already_exists").replace("{name}", fakerCategory);

        ResponseEntity<RestErrorMessage> responseEntity = categoryExceptionsHandler.handleCategoryAlreadyExistsException(categoryAlreadyExistsException);
        
        // Assert that the response status is CONFLICT and the message is the expected one
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test 
    public void testCategoryNotFoundException() {
        CategoryNotFoundException categoryNotFoundException = new CategoryNotFoundException(1L);
        String expectedMessage = bundle.getString("category.not_found").replace("{id}", "1");

        ResponseEntity<RestErrorMessage> responseEntity = categoryExceptionsHandler.handleCategoryNotFoundException(categoryNotFoundException);

        // Assert that the response status is NOT_FOUND and the message is the expected one
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }
}
