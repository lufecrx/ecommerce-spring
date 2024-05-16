package br.com.lufecrx.demo.ecommerce.auth.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.auth.model.UserRole;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserValidatorTest {

    private Validator validator;

    private Faker faker = new Faker();

    String validLogin = "validLogin";
    String validPassword = faker.lorem().characters(10, 15) + "A1#";
    String validEmail = faker.internet().emailAddress();
    LocalDate validBirthDate = LocalDate.now().minusYears(20);
    String validMobilePhone = faker.phoneNumber().cellPhone();

    String invalidLogin = "abc";
    String invalidPassword = faker.lorem().characters(10, 15); // no uppercase, no number, no special character
    String invalidEmail = "invalidEmail";
    LocalDate invalidBirthDate = LocalDate.now().plusDays(1); // future date
    String invalidMobilePhone = "3620"; // only 4 digits

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        validLogin = "validLogin";
        validPassword = faker.lorem().characters(10, 15) + "A1#";
        validEmail = faker.internet().emailAddress();
        validBirthDate = LocalDate.now().minusYears(20);
        validMobilePhone = faker.number().digits(11);

        invalidLogin = "abc";
        invalidPassword = faker.lorem().characters(10, 15); // no uppercase, no number, no special character
        invalidEmail = "invalidEmail";
        invalidBirthDate = LocalDate.now().plusDays(1); // future date
        invalidMobilePhone = "3620"; // only 4 digits
    }

    @Test
    public void testValidUser() {
        User user = User.builder()
                .login(validLogin)
                .password(validPassword)
                .email(validEmail)
                .role(UserRole.USER)
                .birthDate(validBirthDate)
                .mobilePhone(validMobilePhone)
                .build();

        assertTrue(validator.validate(user).isEmpty());
    }

    @Test
    public void testInvalidUsername() {
        User user = User.builder()
                .login(invalidLogin)
                .password(validPassword)
                .email(validEmail)
                .role(UserRole.USER)
                .birthDate(validBirthDate)
                .mobilePhone(validMobilePhone)
                .build();

        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void testInvalidEmail() {
        User user = User.builder()
                .login(validLogin)
                .password(validPassword)
                .email(invalidEmail)
                .role(UserRole.USER)
                .birthDate(validBirthDate)
                .mobilePhone(validMobilePhone)
                .build();

        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void testInvalidBirthDate() {
        User user = User.builder()
                .login(validLogin)
                .password(validPassword)
                .email(validEmail)
                .role(UserRole.USER)
                .birthDate(invalidBirthDate)
                .mobilePhone(validMobilePhone)
                .build();

        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void testInvalidMobilePhone() {
        User user = User.builder()
                .login(validLogin)
                .password(validPassword)
                .email(validEmail)
                .role(UserRole.USER)
                .birthDate(validBirthDate)
                .mobilePhone(invalidMobilePhone)
                .build();

        assertFalse(validator.validate(user).isEmpty());
    }
}