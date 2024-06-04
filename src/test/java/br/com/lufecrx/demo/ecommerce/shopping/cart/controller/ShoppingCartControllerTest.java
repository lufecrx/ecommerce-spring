package br.com.lufecrx.demo.ecommerce.shopping.cart.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import br.com.lufecrx.demo.ecommerce.exception.api.domain.product.ProductNotFoundException;
import br.com.lufecrx.demo.ecommerce.shopping.cart.model.dto.ShoppingCartDTO;
import br.com.lufecrx.demo.ecommerce.shopping.cart.service.ShoppingCartService;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    private ResourceBundle bundle;

    @BeforeEach
    void setUp() {
        bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetCartSuccess() throws Exception {
        ShoppingCartDTO cart = mock(ShoppingCartDTO.class);
        when(shoppingCartService.getCart()).thenReturn(cart);

        mockMvc.perform(get("/shopping-cart/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testAddProductToCartSuccess() throws Exception {
        doNothing().when(shoppingCartService).addProductToCart(anyLong(), anyInt());

        mockMvc.perform(put("/shopping-cart/add")
                .param("product", "1")
                .param("quantity", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(bundle.getString("cart.successfully_added_product")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testRemoveProductFromCartSuccess() throws Exception {
        doNothing().when(shoppingCartService).removeProductFromCart(anyLong());

        mockMvc.perform(put("/shopping-cart/remove")
                .param("product", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(bundle.getString("cart.successfully_deleted_product")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testClearCartSuccess() throws Exception {
        doNothing().when(shoppingCartService).clearCart();

        mockMvc.perform(put("/shopping-cart/clear")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(bundle.getString("cart.successfully_cleared")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testAddProductToCartNotFound() throws Exception {
        doThrow(new ProductNotFoundException(1L)).when(shoppingCartService).addProductToCart(anyLong(), anyInt());

        mockMvc.perform(put("/shopping-cart/add")
                .param("product", "1")
                .param("quantity", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testRemoveProductFromCartNotFound() throws Exception {
        doThrow(new ProductNotFoundException(1L)).when(shoppingCartService).removeProductFromCart(anyLong());

        mockMvc.perform(put("/shopping-cart/remove")
                .param("product", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAcessServiceNotAuthenticated() throws Exception {
        mockMvc.perform(get("/shopping-cart/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}