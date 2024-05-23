package br.com.lufecrx.demo.ecommerce.api.controller.domain.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
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

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.api.model.dto.ProductDTO;
import br.com.lufecrx.demo.ecommerce.api.service.domain.product.ProductServicePaginable;

public class ProductControllerPaginableTest {

    @InjectMocks
    private ProductControllerPaginable productControllerPaginable;

    @Mock
    private ProductServicePaginable productService;

    private MockMvc mockMvc;

    Faker faker = new Faker();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productControllerPaginable).build();
    }

    @Test
    public void testFindAll() throws Exception {

        ProductDTO mockedProduct1 = mock(ProductDTO.class);
        ProductDTO mockedProduct2 = mock(ProductDTO.class);

        // Mock the service method to return a list of products
        when(productService.getWithPagination(anyInt(), anyInt(), any()))
                .thenReturn(Arrays.asList(mockedProduct1, mockedProduct2));

        // Perform a GET request and expect a 200 OK status, simulating a successful
        // request to the endpoint /products/paginable
        mockMvc.perform(get("/products/paginable")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,asc"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearch() throws Exception {

        ProductDTO mockedProduct1 = mock(ProductDTO.class);
        ProductDTO mockedProduct2 = mock(ProductDTO.class);

        // Mock the service method to return a list of products
        when(productService.searchProducts(any(), any(), any(), any(), anyInt(), anyInt(), any()))
                .thenReturn(Arrays.asList(mockedProduct1, mockedProduct2));

        // Perform a GET request and expect a 200 OK status, simulating a successful
        // request to the endpoint /products/paginable/search
        mockMvc.perform(get("/products/paginable/search")
                .param("name", "test")
                .param("category", "testCategory")
                .param("minPrice", "100")
                .param("maxPrice", "200")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,asc"))
                .andExpect(status().isOk());
    }
}