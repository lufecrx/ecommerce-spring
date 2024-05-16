package br.com.lufecrx.demo.ecommerce.exception.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.EmailAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.LoginAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserAlreadyVerifiedException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserNotEnabledException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.auth.handler.UserExceptionsHandler;
import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;

public class UserExceptionsTest {

    private UserExceptionsHandler userExceptionsHandler;
    
    private ResourceBundle bundle;

    private Faker faker;

    @BeforeEach
    public void init() {
        userExceptionsHandler = new UserExceptionsHandler();
        faker = new Faker();
        bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    }

    @Test
    public void testEmailAlreadyExistsException() {
        String fakerEmail = faker.internet().emailAddress();
        EmailAlreadyExistsException emailAlreadyExistsException = new EmailAlreadyExistsException(fakerEmail);
        String expectedMessage = bundle.getString("user.email_already_exists").replace("{email}", fakerEmail);

        ResponseEntity<RestErrorMessage> responseEntity = userExceptionsHandler.handleEmailAlreadyExistsException(emailAlreadyExistsException);

        // Assert that the response status is BAD_REQUEST and the message is the expected one
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test
    public void testLoginAlreadyExistsException() {
        String fakerLogin = faker.funnyName().name();
        LoginAlreadyExistsException emailAlreadyExistsException = new LoginAlreadyExistsException(fakerLogin);
        String expectedMessage = bundle.getString("user.login_already_exists").replace("{login}", fakerLogin);

        ResponseEntity<RestErrorMessage> responseEntity = userExceptionsHandler.handleLoginAlreadyExistsException(emailAlreadyExistsException);

        // Assert that the response status is BAD_REQUEST and the message is the expected one
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test
    public void testUserAlreadyVerifiedException() {
        UserAlreadyVerifiedException userAlreadyVerifiedException = new UserAlreadyVerifiedException();
        String expectedMessage = bundle.getString("user.already_verified");
        assertEquals(expectedMessage, userAlreadyVerifiedException.getLocalizedMessage());

        ResponseEntity<RestErrorMessage> responseEntity = userExceptionsHandler.handleUserAlreadyVerified(userAlreadyVerifiedException);

        // Assert that the response status is BAD_REQUEST and the message is the expected one
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test 
    public void testUserNotFoundWithIdException() {
        UserNotFoundException userNotFoundException = new UserNotFoundException(1L);
        String expectedMessage = bundle.getString("user.with_id_not_found").replace("{id}", "1");

        ResponseEntity<RestErrorMessage> responseEntity = userExceptionsHandler.handleUserNotFoundException(userNotFoundException);

        // Assert that the response status is NOT_FOUND and the message is the expected one
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test
    public void testUserNotFoundWithEmailException() {
        String fakerEmail = faker.internet().emailAddress();
        UserNotFoundException userNotFoundException = new UserNotFoundException(fakerEmail);
        String expectedMessage = bundle.getString("user.with_email_not_found").replace("{email}", fakerEmail);

        ResponseEntity<RestErrorMessage> responseEntity = userExceptionsHandler.handleUserNotFoundException(userNotFoundException);

        // Assert that the response status is NOT_FOUND and the message is the expected one
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
    }

    @Test
    public void testUserNotEnabledException() {
        UserNotEnabledException userNotEnabledException = new UserNotEnabledException();
        String exceptedMessage = bundle.getString("user.not_enabled");

        ResponseEntity<RestErrorMessage> responseEntity = userExceptionsHandler.handleUserNotEnabledException(userNotEnabledException);

        // Assert that the response status is BAD_REQUEST and the message is the expected one
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exceptedMessage, responseEntity.getBody().getMessage());
    }

}
