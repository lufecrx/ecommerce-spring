package br.com.lufecrx.demo.ecommerce.api.service.domain.wishlist;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import br.com.lufecrx.demo.ecommerce.api.repository.WishlistRepository;
import br.com.lufecrx.demo.ecommerce.auth.model.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class WishlistCachingIntegrationTest {

    @Autowired
    private WishlistServicePaginable wishlistServicePag;

    @SpyBean
    private WishlistRepository wishlistRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private EntityManager entityManager;

    private Authentication authentication;

    private SecurityContext securityContext;

    private Faker faker = new Faker();

    private List<Wishlist> wishlists;

    private List<Product> products;

    private User user;

    @BeforeEach
    public void setUp() {
        // Initialize the mocks to Authentication and the SecurityContext
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);

        // Mock the SecurityContext to return the Authentication mock
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Configure the SecurityContextHolder to return the SecurityContext mock
        SecurityContextHolder.setContext(securityContext);

        // Mock the User object to be returned by the Authentication mock
        user = User.builder()
                .login("user" + faker.number().randomNumber(5, true))
                .password(faker.internet().password() + "A1b@")
                .email(faker.internet().emailAddress())
                .build();

        entityManager.persist(user);

        when(authentication.getPrincipal()).thenReturn(user);

        products = fillProducts();
        wishlists = fillWishlists();
        wishlistRepository.saveAll(wishlists);
    }

    @AfterEach
    public void tearDown() {
        wishlistRepository.deleteAll();
        cacheManager.getCache("wishlists").clear();
    }

    public List<Product> fillProducts() {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            String name = faker.commerce().productName();
            product.setProductName(name);
            product.setPrice(faker.number().randomDouble(2, 1, 1000));
            products.add(product);
        }

        return products;
    }

    public List<Wishlist> fillWishlists() {
        List<Wishlist> wishlists = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Wishlist wishlist = new Wishlist();
            String name = faker.commerce().productName();
            wishlist.setName(name);
            wishlist.setUser(user);

            for (int j = 0; j < 5; j++) {
                Product product = products.get(j);
                wishlist.addToWishlist(product);
            }

            wishlists.add(wishlist);
        }

        return wishlists;
    }

    @Test
    public void testCaching() {
        // Call the method 100 times
        for (int i = 0; i < 100; i++) {
            wishlistServicePag.getWithPagination(1, 5, new String[] { "name", "asc" });
        }

        PageRequest pageRequest = PageRequest.of(1, 5, Sort.by("name").ascending());

        // Verify that the method was called only once due to caching
        verify(wishlistRepository, times(1)).findAllByUser(user, pageRequest);
    }

    @Test
    public void testCacheEvict() {
        // Call the method 100 times
        for (int i = 0; i < 100; i++) {
            wishlistServicePag.getWithPagination(1, 5, new String[] { "name", "asc" });
        }

        PageRequest pageRequest = PageRequest.of(1, 5, Sort.by("name").ascending());

        // Verify that the method was called only once due to caching
        verify(wishlistRepository, times(1)).findAllByUser(user, pageRequest);

        // Clear the cache, simulating when a method that modifies the database is
        // called
        cacheManager.getCache("wishlists").clear();

        // Call the method again
        wishlistServicePag.getWithPagination(1, 5, new String[] { "name", "asc" });

        // Verify that the method was called again due to cache eviction
        verify(wishlistRepository, times(2)).findAllByUser(user, pageRequest);
    }

}
