package br.com.lufecrx.demo.ecommerce.auth.model.dto.password;

/**
 * It is a DTO that represents the data needed to request a password reset, contains the email of the user that wants to reset the password.
 * It is used to transfer data between the controller and the service.
 * @param email the email of the user that wants to reset the password.
 * @see PasswordResetDTO
 */
public record PasswordResetRequestDTO(String email) {
}
