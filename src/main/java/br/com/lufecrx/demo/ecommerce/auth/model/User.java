package br.com.lufecrx.demo.ecommerce.auth.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import br.com.lufecrx.demo.ecommerce.auth.util.validator.ValidPassword;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.ShoppingCart;
import br.com.lufecrx.demo.ecommerce.shopping.order.model.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

/**
 * A user entity that implements the UserDetails interface to be used by Spring
 * Security.
 * The entity has a one-to-many relationship with the Wishlist entity.
 * The entity has a OneTimePassword embedded class to store the OTP and its
 * generation time.
 * 
 * Each user has an id, a login, a password, an email, a birth date, a mobile
 * phone, a role, a set of wishlists, a shopping cart and a set of addresses.
 * 
 * @see ShoppingCart
 * @see Wishlist
 * @see Address
 * @see OneTimePassword
 * @see UserRole
 * @see UserDetails
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User implements UserDetails {

    /**
     * The id of the user. It is generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The login of the user. It must be unique and cannot be blank.
     * It must have between 5 and 15 characters.
     */
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters long")
    @Column(unique = true, nullable = false)
    private String login;

    /**
     * The password of the user. It must be valid according to the ValidPassword
     * annotation.
     * 
     * @see ValidPassword
     */
    @ValidPassword
    private String password;

    /**
     * The email of the user. It must be unique and a valid email and cannot be
     * blank.
     */
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * The birth date of the user. It must be in the past.
     */
    @Past(message = "User birth date must be in the past")
    private LocalDate birthDate;

    /**
     * The mobile phone of the user. It must have 11 digits.
     */
    @Pattern(regexp = "^[0-9]{11}$", message = "User mobile phone must have 11 digits")
    private String mobilePhone;

    /**
     * The shopping cart of the user. It is a one-to-one relationship. 
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ShoppingCart shoppingCart;

    /**
     * The wishlists of the user. It is a set of wishlists.
     * It has a maximum size of 10.
     * 
     * @see Wishlist
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Size(max = 10, message = "User can have at most 10 wishlists")
    private Set<Wishlist> wishlists;

    /**
     * The addresses of the user. It is a set of addresses.
     * It has a maximum size of 5.
     * 
     * @see Address
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(max = 5, message = "User can have at most 5 addresses")
    @Builder.Default
    private Set<Address> addresses = new HashSet<>();

    /**
     * A boolean to check if the user is enabled.
     * It is false by default.
     */
    @Column(name = "is_enabled", columnDefinition = "boolean default false")
    private boolean enabled;

    /**
     * The role of the user. It is a UserRole enum that can be either USER or ADMIN.
     */
    private UserRole role;

    /**
     * Embedded class to store the OTP and its generation time.
     */
    @Embedded
    private OneTimePassword otp;

    /**
     * Constructor for the User entity.
     *
     * @param login       login data of the user
     * @param password    password data of the user
     * @param email       email data of the user
     * @param role        role data of the user
     * @param birthDate   birth date data of the user
     * @param mobilePhone mobile phone data of the user
     */
    public User(String login, String password, String email, UserRole role, LocalDate birthDate, String mobilePhone) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.birthDate = birthDate;
        this.mobilePhone = mobilePhone;
    }

    /**
     * Returns the authorities of the user.
     * If the user is an admin, it returns both ROLE_ADMIN and ROLE_USER.
     * If the user is not an admin, it returns only ROLE_USER.
     * 
     * @return a collection of authorities of the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return Set.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return Set.of(() -> "ROLE_USER");
        }
    }

    /**
     * Adds an address to the user's set of addresses.
     * 
     * @param address the address to be added
     */
    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    /**
     * Removes an address from the user's set of addresses.
     * 
     * @param address the address to be removed
     */
    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }

    /**
     * Returns the password of the user.
     */
    @Override
    public String getUsername() {
        return this.login;
    }

    /**
     * Returns a boolean to check if the user is not expired.
     * 
     * @return default true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Returns a boolean to check if the user is not locked.
     * 
     * @return default true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Returns a boolean to check if the user's credentials are not expired.
     * 
     * @return default true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Returns a boolean to check if the user is enabled.
     * 
     * @return the status of the user (enabled or not)
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
