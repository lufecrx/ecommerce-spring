package br.com.lufecrx.demo.ecommerce.exception.auth.domain.authentication;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the exception that is thrown when the OTP is invalid.
 * Reference for the error message in the messages.properties file: auth.invalid_otp
 * 
 */
@Slf4j
public class InvalidOtpException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Constructor for the InvalidOtpException class.
     * It logs the error message.
     */
    public InvalidOtpException() {
        super(bundle.getString("auth.invalid_otp"));
        log.error(bundle.getString("auth.invalid_otp"));
    }

    /**
     * Constructor for the InvalidOtpException class.
     * It logs the error message and the cause of the exception.
     * 
     * @param cause The cause of the exception
     */
    public InvalidOtpException(Throwable cause) {
        super(bundle.getString("auth.invalid_otp"), cause);
        log.error(bundle.getString("auth.invalid_otp"), cause);
    }
}
