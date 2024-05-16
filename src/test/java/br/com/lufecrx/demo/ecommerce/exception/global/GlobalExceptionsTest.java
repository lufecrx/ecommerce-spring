package br.com.lufecrx.demo.ecommerce.exception.global;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import br.com.lufecrx.demo.ecommerce.exception.global.handler.GlobalExceptionsHandler;
import br.com.lufecrx.demo.ecommerce.exception.message.RestErrorMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

public class GlobalExceptionsTest {

    private GlobalExceptionsHandler globalExceptionsHandler;

    @BeforeEach
    public void init() {
        globalExceptionsHandler = new GlobalExceptionsHandler();
    }

    @Test
    public void testHandleMethodArgumentNotValidException() {
        FieldError fieldError = mock(FieldError.class);
        BindingResult bindingResult = mock(BindingResult.class);
        MethodParameter methodParameter = mock(MethodParameter.class);

        when(bindingResult.getFieldError()).thenReturn(fieldError);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        WebRequest request = mock(WebRequest.class);

        ResponseEntity<RestErrorMessage> responseEntity = globalExceptionsHandler
                .handleMethodArgumentNotValidException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("{}", responseEntity.getBody().getMessage());
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testHandleConstraintViolationException() {
        ConstraintViolation constraintViolation = mock(ConstraintViolation.class);
        ConstraintViolationException ex = mock(ConstraintViolationException.class);
        WebRequest request = mock(WebRequest.class);

        String expectedField = "field";
        String expectedMessage = "must not be null";
        String expectedErrorMessage = "{" + expectedField + "=" + expectedMessage + "}";

        when(constraintViolation.getPropertyPath()).thenReturn(PathImpl.createPathFromString(expectedField));
        when(constraintViolation.getMessage()).thenReturn(expectedMessage);
        when(ex.getConstraintViolations()).thenReturn(Set.of(constraintViolation));

        ResponseEntity<RestErrorMessage> responseEntity = globalExceptionsHandler
                .handleConstraintViolationException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedErrorMessage, responseEntity.getBody().getMessage());
    }

    @Test
    public void testHandleResponseStatusException() {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = "An error occurred";
        ResponseStatusException ex = new ResponseStatusException(status, errorMessage);

        ResponseEntity<RestErrorMessage> responseEntity = globalExceptionsHandler.handleResponseStatusException(ex);

        assertEquals(status, responseEntity.getStatusCode());

        assertEquals(errorMessage, responseEntity.getBody().getMessage());
    }
}
