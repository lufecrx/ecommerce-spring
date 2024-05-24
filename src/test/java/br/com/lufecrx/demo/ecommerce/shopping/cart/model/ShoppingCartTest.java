package br.com.lufecrx.demo.ecommerce.shopping.cart.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.auth.model.User;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    @Before
    public void setUp() {
        shoppingCart = new ShoppingCart();
    }

    @Test
    public void testGetTotalPrice() {
        Product product1 = new Product();
        product1.setPrice(100.0);
        CartItem cartItem1 = new CartItem();
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);

        Product product2 = new Product();
        product2.setPrice(200.0);
        CartItem cartItem2 = new CartItem();
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(1);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);
        shoppingCart.setCartItems(cartItems);

        Double totalPrice = shoppingCart.getTotalPrice();

        assertEquals(400.0, totalPrice, 0.001);
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        List<CartItem> cartItems = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        shoppingCart.setId(1L);
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(cartItems);
        shoppingCart.setCreatedAt(createdAt);
        shoppingCart.setUpdatedAt(updatedAt);

        assertEquals(1L, shoppingCart.getId().longValue());
        assertEquals(user, shoppingCart.getUser());
        assertEquals(cartItems, shoppingCart.getCartItems());
        assertEquals(createdAt, shoppingCart.getCreatedAt());
        assertEquals(updatedAt, shoppingCart.getUpdatedAt());
    }
}