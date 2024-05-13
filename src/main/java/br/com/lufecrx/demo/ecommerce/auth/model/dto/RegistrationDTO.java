package br.com.lufecrx.demo.ecommerce.auth.model.dto;

import java.time.LocalDate;

public record RegistrationDTO(
        String login,
        String password,
        String email,
        LocalDate birthDate,
        String mobilePhone
) {

}
