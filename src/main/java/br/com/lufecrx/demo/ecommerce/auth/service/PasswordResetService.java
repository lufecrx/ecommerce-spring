package br.com.lufecrx.demo.ecommerce.auth.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.lufecrx.demo.ecommerce.auth.model.dto.password.PasswordResetDTO;
import br.com.lufecrx.demo.ecommerce.auth.model.dto.password.PasswordResetRequestDTO;
import br.com.lufecrx.demo.ecommerce.auth.repository.UserRepository;
import br.com.lufecrx.demo.ecommerce.auth.util.EmailUtil;
import br.com.lufecrx.demo.ecommerce.auth.util.OtpUtil;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.authentication.InvalidOtpException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.reset.password.PasswordsDoNotMatchException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserNotEnabledException;
import br.com.lufecrx.demo.ecommerce.exception.auth.domain.user.UserNotFoundException;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

/**
 * This service is responsible for handling the password reset operations.
 */
@Service
@Slf4j
public class PasswordResetService {

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private OtpUtil otpUtil;

    @Autowired
    private UserRepository userRepository;

    /**
     * Method to request a password reset by email
     * @param data DTO with the email of the user that wants to reset the password
     * @throws UserNotFoundException if the user with the email is not found
     * @throws UserNotEnabledException if the user is not enabled
     * @throws MessagingException if an error occurs while sending the email
     */
    public void requestReset(PasswordResetRequestDTO data) {
        log.info("Received data to request a password reset");

        var user = userRepository.findByEmail(data.email())
                .orElseThrow(() -> new UserNotFoundException(data.email()));

        if (!user.isEnabled()) {
            throw new UserNotEnabledException();
        }
        
        var oneTimePassword = otpUtil.generateOtp();
        user.setOtp(oneTimePassword);

        userRepository.save(user); 
        
        try {
            emailUtil.sendRecoverPasswordEmail(data.email(), oneTimePassword.otp());
        } catch (MessagingException e) {
            log.error("Error occurred while sending email to reset password");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while sending email to reset password");
        }
    }

    /**
     * Method to reset the password of a user
     * @param email email of the user to reset the password
     * @param token one-time password to reset the password
     * @param data DTO with the new password
     * @throws ResponseStatusException if the OTP or new password are not provided
     * @throws UserNotFoundException if the user with the email is not found
     * @throws InvalidOtpException if the OTP is invalid or expired
     * @throws PasswordsDoNotMatchException if the new password and the confirmation password do not match
     */
    public void reset(String email, String token, PasswordResetDTO data) {
        log.info("Received data to reset a password");

        if (token == null || data == null || data.password() == null || data.confirmPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP and new password must be provided");
        }

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
                
        if (!user.getOtp().otp().equals(token) || !otpUtil.isValidOtp(user.getOtp())) {
            throw new InvalidOtpException(new Throwable("Invalid or expired OTP"));
        } else {

            if (!data.password().equals(data.confirmPassword())) {
                throw new PasswordsDoNotMatchException();
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            user.setPassword(encryptedPassword);
            user.setOtp(null); 

            userRepository.save(user);
        }
    }
}
