package br.com.lufecrx.demo.ecommerce.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

@Embeddable
public record OneTimePassword(String otp, LocalDateTime otpGenerationTime) {

    public OneTimePassword {
        if (otp == null || otpGenerationTime == null) {
            throw new IllegalArgumentException("OTP and generation time must not be null");
        }
    }
}
    