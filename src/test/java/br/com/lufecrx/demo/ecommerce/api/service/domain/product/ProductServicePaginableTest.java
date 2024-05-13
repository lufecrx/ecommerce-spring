package br.com.lufecrx.demo.ecommerce.api.service.domain.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.repository.ProductRepository;
import br.com.lufecrx.demo.ecommerce.api.services.domain.product.ProductServicePaginable;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.product.ProductsEmptyException;

public class ProductServicePaginableTest {

    @InjectMocks
    private ProductServicePaginable productServicePaginable;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWithPaginationReturnsNonEmptyPage() {
        Product product = new Product();
        Page<Product> page = new PageImpl<>(Arrays.asList(product));

        // Mocking the behavior of the methods to simulate a non-empty page
        when(productRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Getting the products
        Iterable<Product> products = productServicePaginable.getWithPagination(0, 1, new String[] { "id", "asc" });

        // Verifying if the methods were called correctly
        assertTrue(products.iterator().hasNext());
        assertEquals(product, products.iterator().next());
    }

    @Test
    public void testGetWithPaginationReturnsEmptyPage() {
        Page<Product> page = Page.empty();

        // mock the behavior of the methods to simulate an empty page
        when(productRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Getting the products
        assertThrows(ProductsEmptyException.class, () -> {
            productServicePaginable.getWithPagination(0, 1, new String[] { "id", "asc" });
        });
    }

    @Test
    public void testGetWithPaginationThrowsException() {
        // Mocking the behavior of the methods to simulate when an exception is thrown
        when(productRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException());

        // Getting the products and expecting an exception
        assertThrows(RuntimeException.class, () -> {
            productServicePaginable.getWithPagination(0, 1, new String[] { "id", "asc" });
        });
    }
    
}
