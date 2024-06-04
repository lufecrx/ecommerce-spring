package br.com.lufecrx.demo.ecommerce.shopping.cart.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart.CartItemNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart.UnauthorizedCartItemUpdateException;
import br.com.lufecrx.demo.ecommerce.shopping.cart.service.CartItemService;

@SpringBootTest
@AutoConfigureMockMvc
public class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartItemService cartItemService;

    private ResourceBundle bundle;

    @BeforeEach
    void setUp() {
        bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testUpdateCartItemQuantitySuccess() throws Exception {
        doNothing().when(cartItemService).updateCartItemQuantity(anyLong(), anyInt());

        mockMvc.perform(put("/cart-item/update")
                .param("item", "1")
                .param("quantity", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(bundle.getString("cart.successfully_updated")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testRemoveCartItemSuccess() throws Exception {
        doNothing().when(cartItemService).deleteCartItem(anyLong());

        mockMvc.perform(put("/cart-item/remove")
                .param("item", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(bundle.getString("cart.successfully_removed")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testUpdateCartItemQuantityNotFound() throws Exception {
        doThrow(new CartItemNotFoundException()).when(cartItemService).updateCartItemQuantity(anyLong(),
                anyInt());

        mockMvc.perform(put("/cart-item/update")
                .param("item", "1")
                .param("quantity", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testRemoveCartItemNotFound() throws Exception {
        doThrow(new CartItemNotFoundException()).when(cartItemService).deleteCartItem(anyLong());

        mockMvc.perform(put("/cart-item/remove")
                .param("item", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testUpdateCartItemQuantityUnauthorized() throws Exception {
        doThrow(new UnauthorizedCartItemUpdateException())
                .when(cartItemService).updateCartItemQuantity(anyLong(), anyInt());

        mockMvc.perform(put("/cart-item/update")
                .param("item", "1")
                .param("quantity", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testRemoveCartItemUnauthorized() throws Exception {
        doThrow(new UnauthorizedCartItemUpdateException())
                .when(cartItemService).deleteCartItem(anyLong());

        mockMvc.perform(put("/cart-item/remove")
                .param("item", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
