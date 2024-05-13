package br.com.lufecrx.demo.ecommerce.auth.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.auth.model.UserRole;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserValidatorTest {

    private Validator validator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUser() {
        User user = new User("validUsername", "ValidPassword123", "validEmail@example.com", UserRole.USER,
                LocalDate.of(1990, 1, 1), "12345678901");

        assertTrue(validator.validate(user).isEmpty());
    }

    @Test
    public void testInvalidUsername() {
        User user = new User("abc", "ValidPassword123", "validEmail@example.com", UserRole.USER,
                LocalDate.of(1990, 1, 1), "12345678901");

        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void testInvalidEmail() {
        User user = new User("validUsername", "ValidPassword123", "invalidEmail", UserRole.USER,
                LocalDate.of(1990, 1, 1), "12345678901");

        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void testInvalidBirthDate() {
        User user = new User("validUsername", "ValidPassword123", "validEmail@example.com", UserRole.USER,
                LocalDate.now().plusDays(1), "12345678901");

        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void testInvalidMobilePhone() {
        User user = new User("validUsername", "ValidPassword123", "validEmail@example.com", UserRole.USER,
                LocalDate.of(1990, 1, 1), "123");

        assertFalse(validator.validate(user).isEmpty());
    }
}