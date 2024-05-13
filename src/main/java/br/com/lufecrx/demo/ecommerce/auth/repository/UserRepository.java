package br.com.lufecrx.demo.ecommerce.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.lufecrx.demo.ecommerce.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    UserDetails findByLogin(String login);

    Optional<User> findByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
}
