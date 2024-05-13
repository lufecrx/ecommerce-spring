package br.com.lufecrx.demo.ecommerce.auth.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

public class UserTest {

    private User user;

    @BeforeEach
    public void init() {
        user = new User("testUser", "testPassword", "test@test.com", UserRole.USER, LocalDate.now().minusYears(20), "12345678901");
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // If the user role is USER, there should be only one authority: ROLE_USER
        assertEquals(1, authorities.size());
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void testGetUsername() {
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        // By default, the user is not enabled
        assertEquals(false, user.isEnabled());
    }
}
