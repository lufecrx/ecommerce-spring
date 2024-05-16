package br.com.lufecrx.demo.ecommerce.api.controller.domain.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.lufecrx.demo.ecommerce.api.model.Category;
import br.com.lufecrx.demo.ecommerce.api.service.domain.category.CategoryServicePaginable;

public class CategoryControllerPaginableTest {

        private MockMvc mockMvc;

        @InjectMocks
        private CategoryControllerPaginable categoryControllerPaginable;

        @Mock
        private CategoryServicePaginable categoryService;

        @BeforeEach
        public void setup() {
                MockitoAnnotations.openMocks(this);
                mockMvc = MockMvcBuilders.standaloneSetup(categoryControllerPaginable).build();
        }

        @Test
        public void testFindAllWhenSuccessful() throws Exception {
                // Mock the service to return a list of categories
                when(categoryService.getWithPagination(anyInt(), anyInt(), any()))
                                .thenReturn(Arrays.asList(new Category(), new Category()));

                // Perform a GET request and expect a 200 OK status, simulating a successful request to the endpoint /categories/paginable
                mockMvc.perform(get("/categories/paginable")
                                .param("page", "1")
                                .param("size", "20")
                                .param("sort", "name,asc"))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[{}, {}]"));
        }
}