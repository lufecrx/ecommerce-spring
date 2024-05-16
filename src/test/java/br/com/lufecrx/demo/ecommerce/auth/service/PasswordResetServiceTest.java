package br.com.lufecrx.demo.ecommerce.auth.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.auth.model.OneTimePassword;
import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.auth.model.dto.password.PasswordResetDTO;
import br.com.lufecrx.demo.ecommerce.auth.model.dto.password.PasswordResetRequestDTO;
import br.com.lufecrx.demo.ecommerce.auth.repository.UserRepository;
import br.com.lufecrx.demo.ecommerce.auth.util.EmailUtil;
import br.com.lufecrx.demo.ecommerce.auth.util.OtpUtil;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.authentication.InvalidOtpException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.reset.password.MissingArgumentsToResetPasswordException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.reset.password.PasswordsDoNotMatchException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserNotEnabledException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class PasswordResetServiceTest {

    @Mock
    private EmailUtil emailUtil;

    @Mock
    private OtpUtil otpUtil;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PasswordResetService passwordResetService;

    Faker faker = new Faker();

    @Test
    public void testRequestResetWhenSuccessful() throws Exception {
        PasswordResetRequestDTO data = new PasswordResetRequestDTO(faker.internet().emailAddress());
        User user = new User();
        user.setEnabled(true);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        OneTimePassword otp = new OneTimePassword("123456", LocalDateTime.now());
        when(otpUtil.generateOtp()).thenReturn(otp);

        passwordResetService.requestReset(data);

        verify(userRepository, times(1)).save(any(User.class)); // user should be saved with the new OTP
        verify(emailUtil, times(1)).sendRecoverPasswordEmail(anyString(), anyString()); // email should be sent
    }

    @Test
    public void testRequestResetWhenUserNotFound() {
        PasswordResetRequestDTO data = new PasswordResetRequestDTO(faker.internet().emailAddress());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> passwordResetService.requestReset(data)); // should throw UserNotFoundException
    }

    @Test
    public void testRequestResetWhenUserNotEnabled() {
        PasswordResetRequestDTO data = new PasswordResetRequestDTO(faker.internet().emailAddress());
        User user = new User();
        user.setEnabled(false);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(UserNotEnabledException.class, () -> passwordResetService.requestReset(data)); // should throw UserNotEnabledException
    }

    @Test
    public void testResetWhenSuccessful() {
        String email = "test@example.com";
        String token = "123456";
        
        String newPassword = faker.lorem().characters(10, 15) + "A2#";
        String confirmPassword = newPassword; // password and confirmPassword are the same

        PasswordResetDTO data = new PasswordResetDTO(newPassword, confirmPassword);
        
        String oldPassword = faker.lorem().characters(10, 15) + "A1#";
        User user = User.builder()
            .email(email)
            .enabled(true)
            .otp(new OneTimePassword(token, LocalDateTime.now()))
            .password(oldPassword)
            .build();
        
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(otpUtil.isValidOtp(any(OneTimePassword.class))).thenReturn(true);

        passwordResetService.reset(email, token, data);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        verify(userRepository, times(1)).save(any(User.class)); // user should be saved
        assertTrue(passwordEncoder.matches(newPassword, user.getPassword())); // password should be updated
        assertFalse(passwordEncoder.matches(oldPassword, user.getPassword())); // old password should not be valid
    }

    @Test
    public void testResetWhenMissingOtp() {
        String password = faker.lorem().characters(10, 15) + "A2#";
        String confirmPassword = password; // password and confirmPassword are the same

        PasswordResetDTO data = new PasswordResetDTO(password, confirmPassword);

        String email = faker.internet().emailAddress();

        assertThrows(MissingArgumentsToResetPasswordException.class, () -> passwordResetService.reset(email, null, data)); // should throw MissingArgumentsToResetPasswordException
    }

    @Test
    public void testResetWhenMissingPassword() {
        String email = faker.internet().emailAddress();
        String token = "123456";

        PasswordResetDTO data = new PasswordResetDTO(null, null);

        assertThrows(MissingArgumentsToResetPasswordException.class, () -> passwordResetService.reset(email, token, data)); // should throw MissingArgumentsToResetPasswordException
    }

    @Test
    public void testResetWhenUserNotFound() {
        String email = faker.internet().emailAddress();
        String token = "123456";    

        String password = faker.lorem().characters(10, 15) + "A2#";
        String confirmPassword = password; // password and confirmPassword are the same

        PasswordResetDTO data = new PasswordResetDTO(password, confirmPassword);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        
        assertThrows(UserNotFoundException.class, () -> passwordResetService.reset(email, token, data)); // should throw UserNotFoundException
    }

    @Test
    public void testResetWhenOtpIsInvalid() {
        String email = faker.internet().emailAddress();
        String token = "123456";

        String password = faker.lorem().characters(10, 15) + "A2#";
        String confirmPassword = password; // password and confirmPassword are the same

        PasswordResetDTO data = new PasswordResetDTO(password, confirmPassword);

        User user = User.builder()
            .email(email)
            .enabled(true)
            .otp(new OneTimePassword(token, LocalDateTime.now()))
            .password(faker.lorem().characters(10, 15) + "A1#")
            .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(otpUtil.isValidOtp(any(OneTimePassword.class))).thenReturn(false);

        assertThrows(InvalidOtpException.class, () -> passwordResetService.reset(email, token, data)); // should throw InvalidOtpException
    }

    @Test
    public void testResetWhenPasswordsDoNotMatch() {
        String email = faker.internet().emailAddress();
        String token = "123456";

        String password = faker.lorem().characters(10, 15) + "A2#";
        String confirmPassword = password + "1"; // password and confirmPassword are different

        PasswordResetDTO data = new PasswordResetDTO(password, confirmPassword);

        User user = User.builder()
            .email(email)
            .enabled(true)
            .otp(new OneTimePassword(token, LocalDateTime.now()))
            .password(faker.lorem().characters(10, 15) + "A1#")
            .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(otpUtil.isValidOtp(any(OneTimePassword.class))).thenReturn(true);

        assertThrows(PasswordsDoNotMatchException.class, () -> passwordResetService.reset(email, token, data)); // should throw PasswordsDoNotMatchException
    }
}