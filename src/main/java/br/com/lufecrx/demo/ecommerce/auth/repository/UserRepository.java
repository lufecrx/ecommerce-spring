package br.com.lufecrx.demo.ecommerce.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.lufecrx.demo.ecommerce.auth.model.User;

/**
 * This interface is a repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long>{

    /**
     * Find a user by login.
     * 
     * @param login The login of the user.
     * @return The UserDetails object of the user.
     */
    UserDetails findByLogin(String login);

    /**
     * Find a user by email.
     * 
     * @param email The email of the user.
     * @return The user object.
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user with the given login exists in the database.
     * 
     * @param login The login of the user.
     */
    boolean existsByLogin(String login);

    /**
     * Check if a user with the given email exists in the database.
     * 
     * @param email The email of the user.
     */
    boolean existsByEmail(String email);
}
