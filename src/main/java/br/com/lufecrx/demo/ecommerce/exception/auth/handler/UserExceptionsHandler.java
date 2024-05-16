package br.com.lufecrx.demo.ecommerce.exception.auth.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.EmailAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.LoginAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserAlreadyVerifiedException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserNotEnabledException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;

/**
 * This class is responsible for handling exceptions related to the user entity.
 */
@ControllerAdvice
public class UserExceptionsHandler extends ResponseEntityExceptionHandler {

    /**
     * This method handles UserNotFoundException. It returns a response with status 404.
     * @param ex UserNotFoundException
     * @return ResponseEntity<RestErrorMessage> with status 404 and the exception message
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleUserNotFoundException(UserNotFoundException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    /**
     * This method handles EmailAlreadyExistsException. It returns a response with status 400.
     * @param ex EmailAlreadyExistsException
     * @return ResponseEntity<RestErrorMessage> with status 400 and the exception message
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    /**
     * This method handles LoginAlreadyExistsException. It returns a response with status 400.
     * @param ex LoginAlreadyExistsException
     * @return ResponseEntity<RestErrorMessage> with status 400 and the exception message
     */
    @ExceptionHandler(LoginAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleLoginAlreadyExistsException(LoginAlreadyExistsException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    /**
     * This method handles UserNotEnabledException. It returns a response with status 400.
     * @param ex UserNotEnabledException
     * @return ResponseEntity<RestErrorMessage> with status 400 and the exception message
     */
    @ExceptionHandler(UserNotEnabledException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleUserNotEnabledException(UserNotEnabledException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    /**
     * This method handles UserAlreadyVerifiedException. It returns a response with status 400.
     * @param ex UserAlreadyVerifiedException
     * @return ResponseEntity<RestErrorMessage> with status 400 and the exception message
     */
    @ExceptionHandler(UserAlreadyVerifiedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleUserAlreadyVerified(UserAlreadyVerifiedException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
}
