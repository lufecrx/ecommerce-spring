package br.com.lufecrx.demo.ecommerce.api.controller.domain.wishlist;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import br.com.lufecrx.demo.ecommerce.api.service.domain.wishlist.WishlistServicePaginable;

public class WishlistControllerPaginableTest {

    @InjectMocks
    private WishlistControllerPaginable wishlistControllerPaginable;

    @Mock
    private WishlistServicePaginable wishlistService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(wishlistControllerPaginable).build();
    }

    @Test
    public void testFindAll() throws Exception {
        // Mock the service method to return a list of products
        when(wishlistService.getWithPagination(anyInt(), eq(10), any()))
                .thenReturn(Arrays.asList(new Wishlist(), new Wishlist()));

        // Perform the GET request to retrieve all products
        mockMvc.perform(get("/paginable/wishlists/page/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllWithPaginationAndSizeFive() throws Exception {
        // Mock the service method to return a list of products
        when(wishlistService.getWithPagination(anyInt(), eq(5), any()))
                .thenReturn(Arrays.asList(new Wishlist(), new Wishlist()));
        
        // Perform the GET request to retrieve all products with pagination and size 5
        mockMvc.perform(get("/paginable/wishlists/page/1/size=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllWithPaginationAndSizeTen() throws Exception {
        // Mock the service method to return a list of products
        when(wishlistService.getWithPagination(anyInt(), eq(10), any()))
                .thenReturn(Arrays.asList(new Wishlist(), new Wishlist()));
        
        // Perform the GET request to retrieve all products with pagination and size 10
        mockMvc.perform(get("/paginable/wishlists/page/1/size=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllWithPaginationAndSizeTwenty() throws Exception {
        // Mock the service method to return a list of products
        when(wishlistService.getWithPagination(anyInt(), eq(20), any()))
                .thenReturn(Arrays.asList(new Wishlist(), new Wishlist()));
        
        // Perform the GET request to retrieve all products with pagination and size 20
        mockMvc.perform(get("/paginable/wishlists/page/1/size=20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}