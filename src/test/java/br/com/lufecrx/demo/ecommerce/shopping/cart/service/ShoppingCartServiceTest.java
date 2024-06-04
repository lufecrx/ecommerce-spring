package br.com.lufecrx.demo.ecommerce.shopping.cart.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.repository.ProductRepository;
import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.CartItem;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.ShoppingCart;
import br.com.lufecrx.demo.ecommerce.shopping.cart.repository.CartItemRepository;
import br.com.lufecrx.demo.ecommerce.shopping.cart.repository.ShoppingCartRepository;

public class ShoppingCartServiceTest {

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetOrCreateShoppingCartForUser() {
        User user = new User();
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(user);
        when(shoppingCartRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

        shoppingCartService.getOrCreateShoppingCartForUser();

        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
    }

    @Test
    public void testAddProductToCart() {
        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(1L);

        ShoppingCart shoppingCart = new ShoppingCart();

        when(authentication.getPrincipal()).thenReturn(user);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(shoppingCartRepository.findByUserId(anyLong())).thenReturn(Optional.of(shoppingCart));

        shoppingCartService.addProductToCart(1L, 5);

        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    public void testRemoveProductFromCart() {
        User user = new User();
        user.setId(1L);
    
        Product product = new Product();
        product.setId(1L);
    
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        shoppingCart.addCartItem(cartItem);
    
        when(authentication.getPrincipal()).thenReturn(user);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(shoppingCartRepository.findByUserId(anyLong())).thenReturn(Optional.of(shoppingCart));
    
        shoppingCartService.removeProductFromCart(1L);
    
        verify(cartItemRepository, times(1)).delete(any(CartItem.class));
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
    }

    @Test
    public void testClearCart() {
        User user = new User();
        user.setId(1L);
        ShoppingCart shoppingCart = new ShoppingCart();

        when(authentication.getPrincipal()).thenReturn(user);
        when(shoppingCartRepository.findByUserId(anyLong())).thenReturn(Optional.of(shoppingCart));

        shoppingCartService.clearCart();

        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
    }

    @Test
    public void testGetCart() {
        User user = new User();
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(user);

        shoppingCartService.getCart();

        verify(shoppingCartRepository, times(1)).findByUserId(anyLong());
    }

}