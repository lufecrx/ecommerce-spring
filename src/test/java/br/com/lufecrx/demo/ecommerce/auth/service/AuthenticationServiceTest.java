package br.com.lufecrx.demo.ecommerce.auth.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import br.com.lufecrx.demo.ecommerce.auth.model.OneTimePassword;
import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.auth.model.dto.RegistrationDTO;
import br.com.lufecrx.demo.ecommerce.auth.repository.UserRepository;
import br.com.lufecrx.demo.ecommerce.auth.util.EmailUtil;
import br.com.lufecrx.demo.ecommerce.auth.util.OtpUtil;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.authentication.InvalidOtpException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.EmailAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.LoginAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserAlreadyVerifiedException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserNotFoundException;
import jakarta.mail.MessagingException;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailUtil emailUtil;

    @Mock
    private OtpUtil otpUtil;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignupUserLoginAlreadyExists() {
        RegistrationDTO data = new RegistrationDTO("test", "test@test.com", "password", LocalDate.now(), "1234567890");

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.existsByLogin(data.login())).thenReturn(true);

        // Requesting the password reset and expecting an exception
        assertThrows(LoginAlreadyExistsException.class, () -> authenticationService.signup(data));

        // Verifying if the methods were called
        verify(userRepository, times(1)).existsByLogin(data.login());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testSignupUserEmailAlreadyExists() {
        RegistrationDTO data = new RegistrationDTO("test", "test@test.com", "password", LocalDate.now(), "1234567890");

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.existsByLogin(data.login())).thenReturn(false);
        when(userRepository.existsByEmail(data.email())).thenReturn(true);

        // Requesting the password reset and expecting an exception
        assertThrows(EmailAlreadyExistsException.class, () -> authenticationService.signup(data));

        // Verifying if the methods were called
        verify(userRepository, times(1)).existsByLogin(data.login());
        verify(userRepository, times(1)).existsByEmail(data.email());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testSignupErrorSendingEmail() throws MessagingException {
        RegistrationDTO data = new RegistrationDTO("test", "test@test.com", "password12@",
                LocalDate.parse("2000-01-01"), "1234567890");

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.existsByLogin(data.login())).thenReturn(false);
        when(userRepository.existsByEmail(data.email())).thenReturn(false);
        when(otpUtil.generateOtp()).thenReturn(new OneTimePassword("123456", LocalDateTime.now()));

        // Mocking the behavior of the method that will throw an exception
        doThrow(MessagingException.class).when(emailUtil).sendOtpEmail(anyString(), anyString());

        // Requesting the password reset and expecting an exception
        assertThrows(ResponseStatusException.class, () -> authenticationService.signup(data));

        // Verifying if the methods were called
        verify(userRepository, times(1)).existsByLogin(data.login());
        verify(userRepository, times(1)).existsByEmail(data.email());
        verify(emailUtil, times(1)).sendOtpEmail(anyString(), anyString());
        verifyNoMoreInteractions(userRepository, emailUtil);
    }

    @Test
    public void testSignupSuccess() throws MessagingException {
        RegistrationDTO data = new RegistrationDTO("test", "test@test.com", "password", LocalDate.now(), "1234567890");

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.existsByLogin(data.login())).thenReturn(false);
        when(userRepository.existsByEmail(data.email())).thenReturn(false);
        doNothing().when(emailUtil).sendOtpEmail(anyString(), anyString());
        when(otpUtil.generateOtp()).thenReturn(new OneTimePassword("123456", LocalDateTime.now()));

        // Requesting the password reset
        authenticationService.signup(data);

        // Verifying if the methods were called
        verify(userRepository, times(1)).existsByLogin(data.login());
        verify(userRepository, times(1)).existsByEmail(data.email());
        verify(emailUtil, times(1)).sendOtpEmail(anyString(), anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepository, emailUtil);
    }

    @Test
    public void testVerifyAccountInvalidOtp() {
        String email = "test@test.com";
        String otp = "123456";

        User user = new User();
        user.setOtp(new OneTimePassword("654321", LocalDateTime.now()));

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Requesting the password reset and expecting an exception
        assertThrows(InvalidOtpException.class, () -> authenticationService.verifyAccount(email, otp));

        // Verifying if the methods were called
        verify(userRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testVerifyAccountUserNotFound() {
        String email = "test@test.com";
        String otp = "123456";

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Requesting the password reset and expecting an exception
        assertThrows(UserNotFoundException.class, () -> authenticationService.verifyAccount(email, otp));

        // Verifying if the methods were called
        verify(userRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testVerifyAccountSuccess() {
        String email = "test@test.com";
        String token = "123456";
        OneTimePassword otp = new OneTimePassword(token, LocalDateTime.now());

        User user = new User();
        user.setOtp(otp);

        // Mocking the behavior of the methods that will be called in the service method
        when(otpUtil.isValidOtp(otp)).thenReturn(true);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Requesting the password reset
        authenticationService.verifyAccount(email, token);

        // Verifying if the methods were called
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testResendVerificationUserAlreadyVerified() {
        String email = "test@test.com";

        User user = new User();
        user.setEnabled(true);

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Requesting the password reset and expecting an exception
        assertThrows(UserAlreadyVerifiedException.class, () -> authenticationService.resendVerification(email));

        // Verifying if the methods were called
        verify(userRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testResendVerificationSuccess() throws MessagingException {
        String email = "test@test.com";

        User user = new User();
        user.setEnabled(false);

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        doNothing().when(emailUtil).sendOtpEmail(anyString(), anyString());
        when(otpUtil.generateOtp()).thenReturn(new OneTimePassword("123456", LocalDateTime.now()));

        // Requesting the password reset
        authenticationService.resendVerification(email);

        // Verifying if the methods were called
        verify(userRepository, times(1)).findByEmail(email);
        verify(emailUtil, times(1)).sendOtpEmail(anyString(), anyString());
    }

}