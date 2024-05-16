package br.com.lufecrx.demo.ecommerce.exception.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoriesEmptyException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoryAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoryNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;

/**
 * This class is responsible for handling exceptions related to the category domain.
 */
@ControllerAdvice
public class CategoryExceptionsHandler extends ResponseEntityExceptionHandler {

    /**
     * This method handles CategoryNotFoundException. It returns a response with status 404.
     * @param ex CategoryNotFoundException
     * @return ResponseEntity<RestErrorMessage> with status 404 and the exception message
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    /**
     * This method handles CategoryAlreadyExistsException. It returns a response with status 409.
     * @param ex CategoryAlreadyExistsException
     * @return ResponseEntity<RestErrorMessage> with status 409 and the exception message
     */
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RestErrorMessage> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    /**
     * This method handles CategoriesEmptyException. It returns a response with status 404.
     * @param ex CategoriesEmptyException
     * @return ResponseEntity<RestErrorMessage> with status 404 and the exception message
     */
    @ExceptionHandler(CategoriesEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleCategoriesEmptyException(CategoriesEmptyException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
}
