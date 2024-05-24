package br.com.lufecrx.demo.ecommerce.shopping.cart.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import br.com.lufecrx.demo.ecommerce.api.model.Product;

public class CartItemTest {

    private CartItem cartItem;
    private Product product;
    private ShoppingCart shoppingCart;

    @Before
    public void setUp() {
        product = mock(Product.class);
        shoppingCart = mock(ShoppingCart.class);
        cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(2);
    }

    @Test
    public void testGetUnitPrice() {
        // Mocking product price
        when(product.getPrice()).thenReturn(10.0);

        // Asserting the unit price
        assertEquals(10.0, cartItem.getUnitPrice(), 0.001);
    }

    @Test
    public void testGetTotalPrice() {
        // Mocking product price
        when(product.getPrice()).thenReturn(10.0);

        // Asserting the total price
        assertEquals(20.0, cartItem.getTotalPrice(), 0.001);
    }

    @Test
    public void testSettersAndGetters() {
        Product newProduct = mock(Product.class);
        ShoppingCart newShoppingCart = mock(ShoppingCart.class);

        cartItem.setId(1L);
        cartItem.setProduct(newProduct);
        cartItem.setShoppingCart(newShoppingCart);
        cartItem.setQuantity(3);

        assertEquals(1L, cartItem.getId().longValue());
        assertEquals(newProduct, cartItem.getProduct());
        assertEquals(newShoppingCart, cartItem.getShoppingCart());
        assertEquals(3, cartItem.getQuantity().intValue());
    }
}
