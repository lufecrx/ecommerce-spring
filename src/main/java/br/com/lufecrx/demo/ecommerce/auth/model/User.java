package br.com.lufecrx.demo.ecommerce.auth.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters long")
    @Column(unique = true, nullable = false)
    private String login;

    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @Past(message = "User birth date must be in the past")
    private LocalDate birthDate;

    @Pattern(regexp = "^[0-9]{11}$", message = "User mobile phone must have 11 digits")
    private String mobilePhone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Wishlist> wishlists;

    
    @Column(name = "is_enabled", columnDefinition = "boolean default false")
    private boolean enabled;
    
    private UserRole role;
    
    // @Embedded // Embeddable class to store the OTP and its generation time in the User entity
    // private OneTimePassword otp;
    

    public User(String login, String password, String email, UserRole role, LocalDate birthDate, String mobilePhone) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.birthDate = birthDate;
        this.mobilePhone = mobilePhone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return Set.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return Set.of(() -> "ROLE_USER");
        }
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
