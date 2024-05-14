package br.com.lufecrx.demo.ecommerce.api.controller.domain.wishlist;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lufecrx.demo.ecommerce.api.model.dto.WishlistDTO;
import br.com.lufecrx.demo.ecommerce.api.service.domain.wishlist.WishlistService;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.product.ProductNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
public class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    private WishlistDTO wishlist;

    @BeforeEach
    public void setUp() {
        // Create a new Wishlist object to use in the tests
        wishlist = new WishlistDTO("Test Wishlist", null);
    }

    // ### Test methods when the user is authenticated as an USER, which has all permissions ###
    @Test
    @WithMockUser(roles = "USER")
    public void testFindByIdAsUser() throws Exception {
        // Simulate the situation where the user is authenticated and is the owner of the wishlist
        when(wishlistService.getWishlistById(1L)).thenReturn(Optional.of(wishlist));

        mockMvc.perform(get("/wishlists/find-wishlist/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // The user is authenticated and is the owner of the wishlist
        
        // Simulate the situation where the user is authenticated and is not the owner of the wishlist
        when(wishlistService.getWishlistById(2L)).thenThrow(WishlistNotFoundException.class);

        mockMvc.perform(get("/wishlists/find-wishlist/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // The user is authenticated but is not the owner of the wishlist
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testFindByNameAsUser() throws Exception {
        // Simulate the situation where the user is authenticated and is the owner of the wishlist
        when(wishlistService.getWishlistByName("Test Wishlist")).thenReturn(Optional.of(wishlist));

        mockMvc.perform(get("/wishlists/find-wishlist-by-name/Test Wishlist")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // The user is authenticated and is the owner of the wishlist
        
        // Simulate the situation where the user is authenticated and is not the owner of the wishlist
        when(wishlistService.getWishlistByName("Test Wishlist 2")).thenThrow(WishlistNotFoundException.class);

        mockMvc.perform(get("/wishlists/find-wishlist-by-name/Test Wishlist 2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // The user is authenticated but is not the owner of the wishlist
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testCreateWishlistAsUser() throws Exception {
        // Simulate the situation where the user is authenticated and tries to access the endpoint
        doNothing().when(wishlistService).createWishlist(wishlist);

        mockMvc.perform(post("/wishlists/add-wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(wishlist)))
                .andExpect(status().isOk()); // The user is authenticated and is allowed to create a wishlist
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRenameWishlistAsUser() throws Exception {
        // Simulate the situation where the user is authenticated and is the owner of the wishlist
        doNothing().when(wishlistService).renameWishlist(1L, wishlist);

        mockMvc.perform(put("/wishlists/update-wishlist/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(wishlist)))
                .andExpect(status().isOk()); // The user is authenticated and is the owner of the wishlist
        

        // Simulate the situation where the user is authenticated and is not the owner of the wishlist
        doThrow(WishlistNotFoundException.class).when(wishlistService).renameWishlist(2L, wishlist);

        mockMvc.perform(put("/wishlists/update-wishlist/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(wishlist)))
                .andExpect(status().isNotFound()); // The user is authenticated but is not the owner of the wishlist


        // Simulate the situation where the user is authenticated and a wishlist with the same name already exists
        doThrow(WishlistAlreadyExistsException.class).when(wishlistService).renameWishlist(3L, wishlist);

        mockMvc.perform(put("/wishlists/update-wishlist/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(wishlist)))
                .andExpect(status().isConflict()); // The user is authenticated but a wishlist with the same name already exists
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testDeleteWishlistAsUser() throws Exception {
        // Simulate the situation where the user is authenticated and is the owner of the wishlist
        doNothing().when(wishlistService).deleteWishlist(1L);

        mockMvc.perform(delete("/wishlists/delete-wishlist/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // The user is authenticated and is the owner of the wishlist


        // Simulate the situation where the user is authenticated and is not the owner of the wishlist
        doThrow(WishlistNotFoundException.class).when(wishlistService).deleteWishlist(2L);

        mockMvc.perform(delete("/wishlists/delete-wishlist/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // The user is authenticated but is not the owner of the wishlist
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testAddProductToWishlistAsUser() throws Exception {
        // Simulate the situation where the user is authenticated and is the owner of the wishlist
        doNothing().when(wishlistService).addProductToWishlist(1L, 1L);

        mockMvc.perform(put("/wishlists/1/add-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // The user is authenticated and is the owner of the wishlist

        
        // Simulate the situation where the user is authenticated and is not the owner of the wishlist
        doThrow(WishlistNotFoundException.class).when(wishlistService).addProductToWishlist(2L, 1L);

        mockMvc.perform(put("/wishlists/2/add-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // The user is authenticated but is not the owner of the wishlist

        
        // Simulate the situation where the user is authenticated and the product is not found
        doThrow(ProductNotFoundException.class).when(wishlistService).addProductToWishlist(3L, 2L);

        mockMvc.perform(put("/wishlists/3/add-product/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // The user is authenticated but the product is not found
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRemoveProductToWishlistAsUser() throws Exception {
        // Simulate the situation where the user is authenticated and is the owner of the wishlist
        doNothing().when(wishlistService).removeProductFromWishlist(1L, 1L);

        mockMvc.perform(put("/wishlists/1/remove-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // The user is authenticated and is the owner of the wishlist

        
        // Simulate the situation where the user is authenticated and is not the owner of the wishlist
        doThrow(WishlistNotFoundException.class).when(wishlistService).removeProductFromWishlist(2L, 1L);

        mockMvc.perform(put("/wishlists/2/remove-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // The user is authenticated but is not the owner of the wishlist

        
        // Simulate the situation where the user is authenticated and the product is not found
        doThrow(ProductNotFoundException.class).when(wishlistService).removeProductFromWishlist(3L, 2L);

        mockMvc.perform(put("/wishlists/3/remove-product/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // The user is authenticated but the product is not found
    }
        
    // ### Test methods when the user is not authenticated ###
    @Test
    public void testFindByIdAsNotAuthenticated() throws Exception {
        // Simulate the situation where the user is not authenticated and tries to access the endpoint
        when(wishlistService.getWishlistById(1L)).thenReturn(Optional.of(wishlist)); 

        // The user needs to be authenticated to access this endpoint and needs to be the owner of the wishlist
        mockMvc.perform(get("/wishlists/find-wishlist/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // In this case, the user is not authenticated
    }

    @Test
    public void testCreateWishlistAsNotAuthenticated() throws Exception {
        // Simulate the situation where the user is not authenticated and tries to access the endpoint
        doNothing().when(wishlistService).createWishlist(wishlist);

        mockMvc.perform(post("/wishlists/add-wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(wishlist)))
                .andExpect(status().isForbidden()); // The user needs to be authenticated to access this endpoint
    }

    @Test
    public void testRenameWishlistAsNotAuthenticated() throws Exception {
        // Simulate the situation where the user is not authenticated and tries to access the endpoint
        doNothing().when(wishlistService).renameWishlist(1L, wishlist);
        
        // The user needs to be authenticated to access this endpoint and needs to be the owner of the wishlist
        mockMvc.perform(put("/wishlists/update-wishlist/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(wishlist)))
                .andExpect(status().isForbidden()); // In this case, the user is not authenticated
    }

    @Test
    public void testDeleteWishlistAsNotAuthenticated() throws Exception {
        // Simulate the situation where the user is not authenticated and tries to access the endpoint
        doNothing().when(wishlistService).deleteWishlist(1L);

        // The user needs to be authenticated to access this endpoint and needs to be the owner of the wishlist
        mockMvc.perform(delete("/wishlists/delete-wishlist/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // In this case, the user is not authenticated
    }

    @Test
    public void testAddProductToWishlistAsNotAuthenticated() throws Exception {
        // Simulate the situation where the user is not authenticated and tries to access the endpoint
        doNothing().when(wishlistService).addProductToWishlist(1L, 1L);

        // The user needs to be authenticated to access this endpoint and needs to be the owner of the wishlist
        mockMvc.perform(put("/wishlists/1/add-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // In this case, the user is not authenticated
    }

    @Test
    public void testRemoveProductToWishlistAsNotAuthenticated() throws Exception {
        // Simulate the situation where the user is not authenticated and tries to access the endpoint
        doNothing().when(wishlistService).removeProductFromWishlist(1L, 1L);

        // The user needs to be authenticated to access this endpoint and needs to be the owner of the wishlist
        mockMvc.perform(delete("/wishlists/1/remove-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // In this case, the user is not authenticated
    }
}