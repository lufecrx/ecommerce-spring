package br.com.lufecrx.demo.ecommerce.exception.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistsEmptyException;
import br.com.lufecrx.demo.ecommerce.exception.api.handler.WishlistExceptionsHandler;
import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;

public class WishlistExceptionsTest {

    private WishlistExceptionsHandler wishlistExceptionsHandler;
    
    private ResourceBundle bundle;

    private Faker faker;

    @BeforeEach
    public void init() {
        wishlistExceptionsHandler = new WishlistExceptionsHandler();
        faker = new Faker();
        bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    }

    @Test
    public void testWishlistEmptyException() {
        WishlistsEmptyException wishlistsEmptyException = new WishlistsEmptyException();
        String expectedMessage = bundle.getString("wishlist.empty_list");

        ResponseEntity<RestErrorMessage> responseEntity = wishlistExceptionsHandler.handleWishlistsEmptyException(wishlistsEmptyException);

        // Assert that the response status is NOT_FOUND and the message is the expected one
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test
    public void testWishlistAlreadyExistsException() {
        String fakerWishlist = faker.commerce().department();
        WishlistAlreadyExistsException wishlistAlreadyExistsException = new WishlistAlreadyExistsException(fakerWishlist);
        String expectedMessage = bundle.getString("wishlist.already_exists").replace("{name}", fakerWishlist);

        ResponseEntity<RestErrorMessage> responseEntity = wishlistExceptionsHandler.handleWishlistAlreadyExistsException(wishlistAlreadyExistsException);

        // Assert that the response status is BAD_REQUEST and the message is the expected one
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test 
    public void testWishlistNotFoundException() {
        WishlistNotFoundException wishlistNotFoundException = new WishlistNotFoundException(1L);
        String expectedMessage = bundle.getString("wishlist.not_found").replace("{id}", "1");

        ResponseEntity<RestErrorMessage> responseEntity = wishlistExceptionsHandler.handleWishlistNotFoundException(wishlistNotFoundException);

        // Assert that the response status is NOT_FOUND and the message is the expected one
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }
}
