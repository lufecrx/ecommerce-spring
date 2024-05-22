package br.com.lufecrx.demo.ecommerce.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.javafaker.Faker;

public class ProductTest {

    private List<Product> products;

    private Faker faker;

    @BeforeEach
    public void init() {
        faker = new Faker();
        products = fillProducts();
    }

    public List<Product> fillProducts() {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setProductName(faker.commerce().productName());
            product.setPrice(faker.number().randomDouble(2, 1, 1000));

            products.add(product);
        }

        return products;
    }

    @Test
    public void testProductName() {
        for (Product product : products) {
            assertNotNull(product.getProductName());
            assertFalse(product.getProductName().isEmpty());
        }
    }

    @Test
    public void testPrice() {
        for (Product product : products) {
            assertNotNull(product.getPrice());
            assertFalse(product.getPrice() < 0);
        }
    }

    @Test
    void testWishlists() {
        Product product = new Product();
        Wishlist wishlist = Mockito.mock(Wishlist.class);
        product.getWishlists().add(wishlist);
        assertTrue(product.getWishlists().contains(wishlist));
    }

    @Test
    public void testCategories() {
        Set<Category> categories = new HashSet<>();
        Category category = Mockito.mock(Category.class);
        categories.add(category);
        for (Product product : products) {
            product.setCategories(categories);
            assertNotNull(product.getCategories());
            assertEquals(1, product.getCategories().size());
        }
    }
}