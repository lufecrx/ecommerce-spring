package br.com.lufecrx.demo.ecommerce.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.lufecrx.demo.ecommerce.auth.repository.UserRepository;

/**
 * This service is responsible for handling the authorization operations.
 */
@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * This method will load the user by its username.
     * @param username Username to load the user
     * @throws UsernameNotFoundException if the user is not found
     * @return UserDetails object with the user information
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
    
}