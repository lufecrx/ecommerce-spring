package br.com.lufecrx.demo.ecommerce.shopping.cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.ShoppingCart;

@DataJpaTest
public class ShoppingCartRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private Faker faker = new Faker();

    private User user;

    @BeforeEach
    void setUp() {
        shoppingCartRepository.deleteAll();
        entityManager.clear();
    
        user = new User();
        user.setLogin(faker.lorem().characters(5, 14));
        user.setPassword(faker.internet().password() + "passwordA1@");
        user.setEmail(faker.internet().emailAddress());
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    public void testFindByIdAndUserId() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        ShoppingCart savedShoppingCart = entityManager.persist(shoppingCart);
        entityManager.flush();

        Optional<ShoppingCart> foundShoppingCart = shoppingCartRepository.findByIdAndUserId(savedShoppingCart.getId(), savedShoppingCart.getUser().getId());

        assertThat(foundShoppingCart).isPresent();
        assertThat(foundShoppingCart.get()).isEqualTo(savedShoppingCart);
    }

    @Test
    public void testFindByUserId() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        ShoppingCart savedShoppingCart = entityManager.persist(shoppingCart);
        entityManager.flush();

        Optional<ShoppingCart> foundShoppingCart = shoppingCartRepository.findByUserId(savedShoppingCart.getUser().getId());

        assertThat(foundShoppingCart).isPresent();
        assertThat(foundShoppingCart.get()).isEqualTo(savedShoppingCart);
    }
}