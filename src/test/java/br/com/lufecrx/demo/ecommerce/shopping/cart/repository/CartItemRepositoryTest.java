package br.com.lufecrx.demo.ecommerce.shopping.cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.CartItem;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.ShoppingCart;

@DataJpaTest
public class CartItemRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CartItemRepository cartItemRepository;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        cartItemRepository.deleteAll();
        entityManager.clear();
    }

    @Test
    void testSaveAndFindCartItem() {
        // Created instances of User, Product, ShoppingCart and CartItem    
        User user = new User();
        user.setLogin(faker.lorem().characters(5, 14));
        user.setPassword(faker.internet().password() + "passwordA1@");
        user.setEmail(faker.internet().emailAddress());
        entityManager.persist(user);
        entityManager.flush();

        Product product = new Product();
        product.setProductName(faker.commerce().productName());
        Double price = faker.number().randomDouble(2, 1, 100);
        product.setPrice(price); 
        entityManager.persist(product);
        entityManager.flush();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        entityManager.persist(shoppingCart);
        entityManager.flush();

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(2);

        // Save the CartItem
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        // Verify if the CartItem was saved correctly
        assertThat(savedCartItem).isNotNull();
        assertThat(savedCartItem.getId()).isNotNull();
        assertThat(savedCartItem.getProduct()).isEqualTo(product);
        assertThat(savedCartItem.getShoppingCart()).isEqualTo(shoppingCart);
        assertThat(savedCartItem.getQuantity()).isEqualTo(2);
        assertThat(savedCartItem.getUnitPrice()).isEqualTo(price);
        assertThat(savedCartItem.getTotalPrice()).isEqualTo(price * 2);

        // Find the CartItem by its ID
        CartItem foundCartItem = cartItemRepository.findById(savedCartItem.getId()).orElse(null);

        // Verify if the CartItem was found correctly
        assertThat(foundCartItem).isNotNull();
        assertThat(foundCartItem.getId()).isEqualTo(savedCartItem.getId());
    }

    @Test
    void testFindAllCartItems() {
        // Created instances of User, Product, ShoppingCart and CartItem
        for (int i = 1; i <= 3; i++) {
            Product product = new Product();
            product.setProductName(faker.lorem().characters(5, 14));
            product.setPrice(faker.number().randomDouble(2, 1, 100));
            entityManager.persist(product);
            entityManager.flush();

            User user = new User();
            user.setLogin(faker.lorem().characters(5, 14));
            user.setPassword(faker.internet().password() + "passwordA@" + i);
            user.setEmail(faker.internet().emailAddress());
            entityManager.persist(user);
            entityManager.flush();

            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            entityManager.persist(shoppingCart);
            entityManager.flush();

            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setShoppingCart(shoppingCart);
            cartItem.setQuantity(i);

            cartItemRepository.save(cartItem);
        }

        // Find all CartItem
        List<CartItem> cartItems = cartItemRepository.findAll();

        // Verify if all CartItem were found correctly
        assertThat(cartItems).hasSize(3);
    }
}
