package br.com.lufecrx.demo.ecommerce.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import br.com.lufecrx.demo.ecommerce.auth.model.User;

@DataJpaTest
public class WishlistRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WishlistRepository wishlistRepository;

    private Wishlist wishlist;

    private User user;

    private Faker faker = new Faker();

    @BeforeEach
    public void setUp() {
        entityManager.clear();

        user = User.builder()
            .login(faker.lorem().characters(5, 15)) // valid login must have between 5 and 15 characters
            .email(faker.internet().emailAddress())
            .password(faker.lorem().characters(10, 15) + "A1#")
            .build();

        entityManager.persistAndFlush(user);

        wishlist = Wishlist.builder()
            .name(faker.commerce().productName())
            .user(user)
            .build();
    }

    @Test
    public void whenExistsByName_thenReturnTrue() {
        Wishlist savedWishlist = entityManager.persistAndFlush(wishlist);

        boolean exists = wishlistRepository.existsByName(savedWishlist.getName());

        assertThat(exists).isTrue();
    }

    @Test
    public void whenNotExistsByName_thenReturnFalse() {
        boolean exists = wishlistRepository.existsByName(wishlist.getName());

        assertThat(exists).isFalse();
    }

    @Test
    public void whenFindByName_thenReturnWishlist() {
        Wishlist savedWishlist = entityManager.persistAndFlush(wishlist);

        Optional<Wishlist> foundWishlist = wishlistRepository.findByName(savedWishlist.getName());

        assertThat(foundWishlist.isPresent()).isTrue();
        assertThat(foundWishlist.get().getName()).isEqualTo(savedWishlist.getName());
    }

    @Test
    public void whenNotFindByName_thenReturnEmpty() {
        Optional<Wishlist> foundWishlist = wishlistRepository.findByName(wishlist.getName());

        assertThat(foundWishlist.isEmpty()).isTrue();
    }

    @Test
    public void whenExistsByNameAndUser_thenReturnTrue() {
        Wishlist savedWishlist = entityManager.persistAndFlush(wishlist);

        boolean exists = wishlistRepository.existsByNameAndUser(savedWishlist.getName(), user);

        assertThat(exists).isTrue();
    }

    @Test
    public void whenNotExistsByNameAndUser_thenReturnFalse() {
        boolean exists = wishlistRepository.existsByNameAndUser(wishlist.getName(), user);

        assertThat(exists).isFalse();
    }
}