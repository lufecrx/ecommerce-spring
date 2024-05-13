package br.com.lufecrx.demo.ecommerce.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.javafaker.Faker;

public class WishlistTest {

    private Wishlist wishlist;
    
    private Product product;
    
    private Faker faker;

    @BeforeEach
    public void init() {
        wishlist = new Wishlist();
        product = Mockito.mock(Product.class);
        faker = new Faker();
    }

    @Test
    public void testAddToWishlist() {
        wishlist.addToWishlist(product);
        assertNotNull(wishlist.getProducts());
        assertTrue(wishlist.getProducts().contains(product));
    }

    @Test
    public void testRemoveFromWishlist() {
        wishlist.addToWishlist(product);
        wishlist.removeFromWishlist(product);
        assertFalse(wishlist.getProducts().contains(product));
    }

    @Test
    public void testNameValidation() {
        String fakeName = faker.commerce().productName();
        wishlist.setName(fakeName);
        assertEquals(fakeName, wishlist.getName());
    }
}