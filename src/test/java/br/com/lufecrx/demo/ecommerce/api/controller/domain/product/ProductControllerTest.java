package br.com.lufecrx.demo.ecommerce.api.controller.domain.product;

import static org.mockito.Mockito.doNothing;
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

import br.com.lufecrx.demo.ecommerce.api.model.dto.ProductDTO;
import br.com.lufecrx.demo.ecommerce.api.service.domain.product.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductDTO dto;

    @BeforeEach
    public void setUp() {
        // Create a new ProductDTO object to use in the tests
        dto = new ProductDTO("Test Product", 10.0, null);

        // Mock the ProductService methods  
        when(productService.getProductById(1L)).thenReturn(Optional.of(dto));
        doNothing().when(productService).createProduct(dto);
        doNothing().when(productService).updateProduct(1L, dto);
        doNothing().when(productService).deleteProduct(1L);
    }

    // ### Test methods when the user is authenticated as an ADMIN, which has all the permissions ###
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateProductAsAdmin() throws Exception {
        mockMvc.perform(post("/products/add-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk()); // The admin user is allowed to create a product
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testFindProductByIdAsAdmin() throws Exception {
        mockMvc.perform(get("/products/find-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // This endpoint is public
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    ProductDTO response = new ObjectMapper().readValue(json, ProductDTO.class);
                    assert response.name().equals("Test Product");
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateProductAsAdmin() throws Exception {
        mockMvc.perform(put("/products/update-product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk()); // The admin user is allowed to update a product
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteProductAsAdmin() throws Exception {
        mockMvc.perform(delete("/products/delete-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // The admin user is allowed to delete a product
    }

    // ### Test methods when the user is authenticated as a USER, which has limited permissions ###
    @Test
    @WithMockUser(roles = "USER")
    public void testCreateProductAsUser() throws Exception {
        mockMvc.perform(post("/products/add-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isForbidden()); // The user is prohibited from creating a product
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testFindProductByIdAsUser() throws Exception {
        mockMvc.perform(get("/products/find-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // The user is allowed to find a product
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    ProductDTO response = new ObjectMapper().readValue(json, ProductDTO.class);
                    assert response.name().equals("Test Product");
                });
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUpdateProductAsUser() throws Exception {
        mockMvc.perform(put("/products/update-product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isForbidden()); // The user is prohibited from updating a product
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testDeleteProductAsUser() throws Exception {
        mockMvc.perform(delete("/products/delete-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // The user is prohibited from deleting a product
    }

    // ### Test methods when the user is not authenticated ###	
    @Test
    public void testCreateProductAsNotAuthenticated() throws Exception {
        mockMvc.perform(post("/products/add-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isForbidden()); // The guest user is prohibited from creating a product
    }

    @Test
    public void testFindProductByIdAsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/products/find-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // The guest user is allowed to find a product
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    ProductDTO response = new ObjectMapper().readValue(json, ProductDTO.class);
                    assert response.name().equals("Test Product");
                });
    }

    @Test
    public void testUpdateProductAsNotAuthenticated() throws Exception {
        mockMvc.perform(put("/products/update-product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isForbidden()); // The guest user is prohibited from updating a product
    }

    @Test
    public void testDeleteProductAsNotAuthenticated() throws Exception {
        mockMvc.perform(delete("/products/delete-product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // The guest user is prohibited from deleting a product
    }
}