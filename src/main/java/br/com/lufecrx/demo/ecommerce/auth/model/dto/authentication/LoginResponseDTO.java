package br.com.lufecrx.demo.ecommerce.auth.model.dto.authentication;

/**
 * It is a DTO that represents the response of a login request, contains the token of the authenticated user.
 * It is used to transfer data between the controller and the service.
 * @param token the token of the authenticated user.
 * @see LoginDTO
 */
public record LoginResponseDTO(String token) {
    
}
