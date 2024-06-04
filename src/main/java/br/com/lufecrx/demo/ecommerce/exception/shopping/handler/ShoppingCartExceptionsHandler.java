package br.com.lufecrx.demo.ecommerce.exception.shopping.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;
import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart.CartItemNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart.UnauthorizedCartItemUpdateException;

/**
 * This class is responsible for handling exceptions related to shopping cart.
 */
@ControllerAdvice
public class ShoppingCartExceptionsHandler extends ResponseEntityExceptionHandler {

    /**
     * This method handles UnauthorizedCartItemUpdateException. It returns a response with status 401.
     * @return ResponseEntity<RestErrorMessage> with status 401 and the exception message
     */
    @ExceptionHandler(UnauthorizedCartItemUpdateException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<RestErrorMessage> handleUnauthorizedCartItemUpdateException(UnauthorizedCartItemUpdateException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    /**
     * This method handles CartItemNotFoundException. It returns a response with status 404.
     * @return ResponseEntity<RestErrorMessage> with status 404 and the exception message
     */
    @ExceptionHandler(CartItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleCartItemNotFoundException(CartItemNotFoundException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
    
}
