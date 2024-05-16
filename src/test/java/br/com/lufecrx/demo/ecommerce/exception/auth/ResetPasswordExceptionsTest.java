package br.com.lufecrx.demo.ecommerce.exception.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.lufecrx.demo.ecommerce.exception.auth.domain.reset.password.MissingArgumentsToResetPasswordException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.reset.password.PasswordsDoNotMatchException;
import br.com.lufecrx.demo.ecommerce.exception.auth.handler.ResetPasswordExceptionsHandler;
import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;

public class ResetPasswordExceptionsTest {

    private ResetPasswordExceptionsHandler resetPasswordExceptionsHandler;

    private ResourceBundle bundle;

    @BeforeEach
    public void init() {
        resetPasswordExceptionsHandler = new ResetPasswordExceptionsHandler();
        bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    }
    
    @Test
    public void testHandleMissingArgumentsToResetPasswordException() {
        MissingArgumentsToResetPasswordException missingArgumentsToResetPasswordException = new MissingArgumentsToResetPasswordException();
        String expectedMessage = bundle.getString("password_reset.arguments_missing");

        ResponseEntity<RestErrorMessage> responseEntity = resetPasswordExceptionsHandler.handleMissingArgumentsToResetPasswordException(missingArgumentsToResetPasswordException);

        // Assert that the response status is BAD_REQUEST and the message is the expected one
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test
    public void testPasswordsDoNotMatchException() {
        PasswordsDoNotMatchException exception = new PasswordsDoNotMatchException();
        String expectedMessage = bundle.getString("password_reset.must_match");

        ResponseEntity<RestErrorMessage> responseEntity = resetPasswordExceptionsHandler.handlePasswordsDoNotMatchException(exception);

        // Assert that the response status is BAD_REQUEST and the message is the expected one
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }
}
