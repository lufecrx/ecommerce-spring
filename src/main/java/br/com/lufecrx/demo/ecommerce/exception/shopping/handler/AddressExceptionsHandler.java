package br.com.lufecrx.demo.ecommerce.exception.shopping.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;
import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.order.address.AddressListEmptyException;
import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.order.address.AddressNotFoundException;

/**
 * This class is responsible for handling exceptions related to address.
 */
@ControllerAdvice
public class AddressExceptionsHandler extends ResponseEntityExceptionHandler {
   
    /**
     * This method handles AddressNotFoundException. It returns a response with status 404.
     * @param ex AddressNotFoundException
     * @return ResponseEntity<RestErrorMessage> with status 404 and the exception message
     */
    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleAddressNotFoundException(AddressNotFoundException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    /**
     * This method handles AddressListEmptyException. It returns a response with status 404.
     * @param ex AddressListEmptyException
     * @return ResponseEntity<RestErrorMessage> with status 404 and the exception message
     */
    @ExceptionHandler(AddressListEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleAddressListEmptyException(AddressListEmptyException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }


}
