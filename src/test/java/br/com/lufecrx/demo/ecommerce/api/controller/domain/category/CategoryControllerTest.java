package br.com.lufecrx.demo.ecommerce.api.controller.domain.category;

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

import br.com.lufecrx.demo.ecommerce.api.model.dto.CategoryDTO;
import br.com.lufecrx.demo.ecommerce.api.service.domain.category.CategoryService;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private CategoryDTO dto;

    @BeforeEach
    public void setUp() {
        // Create a new CategoryDTO object to use in the tests
        dto = new CategoryDTO("Test Category");
        
        // Mock the CategoryService methods
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(dto));
        doNothing().when(categoryService).createCategory(dto);
        doNothing().when(categoryService).renameCategory(1L, dto);
        doNothing().when(categoryService).deleteCategory(1L);
    }

    // ### Test methods when the user is authenticated as an ADMIN, which has all the permissions ###
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testFindByIdAsAdmin() throws Exception {
        mockMvc.perform(get("/categories/find-category/" + 1L))
                .andExpect(status().isOk()) // Dont need authentication to access this endpoint
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    CategoryDTO response = new ObjectMapper().readValue(json, CategoryDTO.class);
                    assert response.name().equals("Test Category");
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateCategoryAsAdmin() throws Exception {
        mockMvc.perform(post("/categories/add-category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCategoryAsAdmin() throws Exception {
        mockMvc.perform(put("/categories/update-category/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCategoryAsAdmin() throws Exception {
        mockMvc.perform(delete("/categories/delete-category/" + 1L))
                .andExpect(status().isOk());
    }

    // ### Test methods when the user is authenticated as a USER, which has limited permissions ###
    @Test
    @WithMockUser(roles = "USER")
    public void testFindByIdAsUser() throws Exception {
        mockMvc.perform(get("/categories/find-category/" + 1L))
                .andExpect(status().isOk()) // Dont need authentication to access this endpoint
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    CategoryDTO response = new ObjectMapper().readValue(json, CategoryDTO.class);
                    assert response.name().equals("Test Category");
                });
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testCreateCategoryAsUser() throws Exception {
        mockMvc.perform(post("/categories/add-category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                // The user is prohibited from creating a category
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUpdateCategoryAsUser() throws Exception {
        mockMvc.perform(put("/categories/update-category/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                // The user is prohibited from updating a category
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testDeleteCategoryAsUser() throws Exception {
        mockMvc.perform(delete("/categories/delete-category/" + 1L))
                // The user is prohibited from deleting a category
                .andExpect(status().isForbidden());
    }

    // ### Test methods when the user is not authenticated ###
    @Test
    public void testFindByIdAsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/categories/find-category/" + 1L))
                .andExpect(status().isOk()) // Dont need authentication to access this endpoint
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    CategoryDTO response = new ObjectMapper().readValue(json, CategoryDTO.class);
                    assert response.name().equals("Test Category");
                });
    }

    @Test
    public void testCreateCategoryAsNotAuthenticated() throws Exception {
        mockMvc.perform(post("/categories/add-category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                // The guest user is prohibited from creating a category
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdateCategoryAsNotAuthenticated() throws Exception {
        mockMvc.perform(put("/categories/update-category/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                // The guest user is prohibited from updating a category
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteCategoryAsNotAuthenticated() throws Exception {
        mockMvc.perform(delete("/categories/delete-category/" + 1L))
                // The guest user is prohibited from deleting a category
                .andExpect(status().isForbidden());
    }
}