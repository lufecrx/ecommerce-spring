package br.com.lufecrx.demo.ecommerce.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

/**
 * Class to represent the one-time password.
 * @param otp the one-time password.
 * @param otpGenerationTime the generation time of the one-time password.
 */
@Embeddable
public record OneTimePassword(
    String otp, 
    LocalDateTime otpGenerationTime) {

}
    