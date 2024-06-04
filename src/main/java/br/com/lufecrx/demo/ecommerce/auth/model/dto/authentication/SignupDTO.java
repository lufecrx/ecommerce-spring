package br.com.lufecrx.demo.ecommerce.auth.model.dto.authentication;

import java.time.LocalDate;

/**
 * It is a DTO that represents the data needed to register a user, contains the login, password, email, birth date and mobile phone.
 * It is used to transfer data between the controller and the service.
 * @param login the login of the user.
 * @param password the password of the user.
 * @param email the email of the user.
 * @param birthDate the birth date of the user.
 * @param mobilePhone the mobile phone of the user.
 */
public record SignupDTO(
        String login,
        String password,
        String email,
        LocalDate birthDate,
        String mobilePhone
) {

}
