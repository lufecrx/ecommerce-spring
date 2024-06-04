package br.com.lufecrx.demo.ecommerce.exception.shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;
import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart.CartItemNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart.UnauthorizedCartItemUpdateException;
import br.com.lufecrx.demo.ecommerce.exception.shopping.handler.ShoppingCartExceptionsHandler;

public class ShoppingCartExceptionsHandlerTest {

    private ShoppingCartExceptionsHandler shoppingCartExceptionsHandler = new ShoppingCartExceptionsHandler();

    private ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @Test
    public void testUnauthorizedCartItemUpdateException() {
        UnauthorizedCartItemUpdateException unauthorizedCartItemUpdateException = new UnauthorizedCartItemUpdateException();
        String expectedMessage = bundle.getString("cart.not_allowed");

        ResponseEntity<RestErrorMessage> responseEntity = shoppingCartExceptionsHandler.handleUnauthorizedCartItemUpdateException(unauthorizedCartItemUpdateException);

        // Assert that the response status is NOT_FOUND and the message is the expected one
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test
    public void testCartItemNotFoundException() {
        CartItemNotFoundException cartItemNotFoundException = new CartItemNotFoundException();
        String expectedMessage = bundle.getString("cart.not_found");

        ResponseEntity<RestErrorMessage> responseEntity = shoppingCartExceptionsHandler.handleCartItemNotFoundException(cartItemNotFoundException);

        // Assert that the response status is NOT_FOUND and the message is the expected one
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }


    
}
