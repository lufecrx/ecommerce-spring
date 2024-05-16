package br.com.lufecrx.demo.ecommerce.exception.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistsEmptyException;
import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;

/**
 * This class is responsible for handling exceptions related to wishlists.
 */
@ControllerAdvice
public class WishlistExceptionsHandler {

    /**
     * This method handles WishlistNotFoundException. It returns a response with status 404.
     * @param ex WishlistNotFoundException
     * @return ResponseEntity<RestErrorMessage> with status 404 and the exception message
     */
    @ExceptionHandler(WishlistNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleWishlistNotFoundException(WishlistNotFoundException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    /**
     * This method handles WishlistsEmptyException. It returns a response with status 404.
     * @param ex WishlistsEmptyException
     * @return ResponseEntity<RestErrorMessage> with status 404 and the exception message
     */
    @ExceptionHandler(WishlistsEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleWishlistsEmptyException(WishlistsEmptyException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    /**
     * This method handles WishlistAlreadyExistsException. It returns a response with status 409.
     * @param ex WishlistAlreadyExistsException
     * @return ResponseEntity<RestErrorMessage> with status 409 and the exception message
     */
    @ExceptionHandler(WishlistAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleWishlistAlreadyExistsException(WishlistAlreadyExistsException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }
}
