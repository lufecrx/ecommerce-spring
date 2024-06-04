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

import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.CartItem;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.ShoppingCart;
import br.com.lufecrx.demo.ecommerce.shopping.cart.repository.CartItemRepository;

public class CartItemServiceTest {

    @InjectMocks
    private CartItemService cartItemService;

    @Mock
    private CartItemRepository cartItemRepository;

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
    public void testUpdateCartItemQuantity() {
        User user = new User();
        user.setId(1L);

        CartItem cartItem = new CartItem();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        cartItem.setShoppingCart(shoppingCart);

        when(authentication.getPrincipal()).thenReturn(user);
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(cartItem));

        cartItemService.updateCartItemQuantity(1L, 5);

        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    public void testDeleteCartItem() {
        User user = new User();
        user.setId(1L);

        CartItem cartItem = new CartItem();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        cartItem.setShoppingCart(shoppingCart);

        when(authentication.getPrincipal()).thenReturn(user);
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(cartItem));

        cartItemService.deleteCartItem(1L);

        verify(cartItemRepository, times(1)).delete(any(CartItem.class));
    }
}