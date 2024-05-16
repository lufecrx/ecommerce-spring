package br.com.lufecrx.demo.ecommerce.api.controller.domain.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.service.domain.product.ProductServicePaginable;

public class ProductControllerPaginableTest {

    @InjectMocks
    private ProductControllerPaginable productControllerPaginable;

    @Mock
    private ProductServicePaginable productService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productControllerPaginable).build();
    }

    @Test
    public void testFindAll() throws Exception {
        // Mock the service method to return a list of products
        when(productService.getWithPagination(anyInt(), anyInt(), any()))
                .thenReturn(Arrays.asList(new Product(), new Product()));

        // Perform a GET request and expect a 200 OK status, simulating a successful request to the endpoint /products/paginable
        mockMvc.perform(get("/products/paginable")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,asc"))
                .andExpect(status().isOk());
    }
}