package br.com.lufecrx.demo.ecommerce.api.controller.domain.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.http.MediaType;
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
        public void testFindAll() throws Exception {
            // Mock the service to return a list of categories
            when(categoryService.getWithPagination(anyInt(), eq(10), any()))
                    .thenReturn(Arrays.asList(new Category(), new Category()));
        
            // Perform a GET request and expect a 200 OK status, simulating a request to /paginable/categories/page/1
            mockMvc.perform(get("/paginable/categories/page/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{}, {}]"));
        }

        @Test
        public void testFindAllWithPaginationAndSizeFive() throws Exception {
                // Mock the service to return a list of categories
                when(categoryService.getWithPagination(anyInt(), eq(5), any()))
                                .thenReturn(Arrays.asList(new Category(), new Category()));
                
                // Perform a GET request and expect a 200 OK status, simulating a request to /paginable/categories/page/1/size=5
                mockMvc.perform(get("/paginable/categories/page/1/size=5")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[{}, {}]"));
        }

        @Test
        public void testFindAllWithPaginationAndSizeTen() throws Exception {
                // Mock the service to return a list of categories
                when(categoryService.getWithPagination(anyInt(), eq(10), any()))
                                .thenReturn(Arrays.asList(new Category(), new Category()));

                // Perform a GET request and expect a 200 OK status, simulating a request to /paginable/categories/page/1/size=10
                mockMvc.perform(get("/paginable/categories/page/1/size=10")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[{}, {}]"));
        }

        @Test
        public void testFindAllWithPaginationAndSizeTwenty() throws Exception {
                // Mock the service to return a list of categories
                when(categoryService.getWithPagination(anyInt(), eq(20), any()))
                                .thenReturn(Arrays.asList(new Category(), new Category()));

                // Perform a GET request and expect a 200 OK status, simulating a request to /paginable/categories/page/1/size=20
                mockMvc.perform(get("/paginable/categories/page/1/size=20")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json("[{}, {}]"));
        }
}