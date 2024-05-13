package br.com.lufecrx.demo.ecommerce.api.service.domain.product;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.repository.ProductRepository;
import br.com.lufecrx.demo.ecommerce.api.services.domain.product.ProductService;
import br.com.lufecrx.demo.ecommerce.api.services.domain.product.ProductServicePaginable;


@SpringBootTest
public class ProductCachingTest {

    @Autowired
    private ProductServicePaginable productServicePaginable;

    @Autowired
    private ProductService productService;

    @Autowired
    private CacheManager cacheManager;

    @SpyBean
    private ProductRepository productRepository;

    private Faker faker;

    private List<Product> products;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
        products = fillProducts();
        productRepository.saveAll(products);
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
        cacheManager.getCache("products").clear();
    }

    public List<Product> fillProducts() {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            String productName = faker.commerce().productName();
            Double price = faker.number().randomDouble(2, 0, 1000);

            product.setProductName(productName);
            product.setPrice(price);
            products.add(product);
        }

        return products;
    }

    @Test
    public void testCaching() {
        // Call the method 100 times
        for (int i = 0; i < 100; i++) {
            productServicePaginable.getWithPagination(1, 5, new String[] { "productName", "asc" });
        }

        PageRequest pageRequest = PageRequest.of(1, 5, Sort.by("productName").ascending());

        // Verify that the method was called only once due to caching
        verify(productRepository, times(1)).findAll(pageRequest);
    }

    @Test
    public void testCacheEvict() {
        // Call the method 100 times
        for (int i = 0; i < 100; i++) {
            productServicePaginable.getWithPagination(1, 5, new String[] { "productName", "asc" });
        }

        PageRequest pageRequest = PageRequest.of(1, 5, Sort.by("productName").ascending());

        // Verify that the method was called only once due to caching
        verify(productRepository, times(1)).findAll(pageRequest);

        // Delete a wishlist to evict the cache
        productService.deleteProduct(products.get(0).getId());

        // Call the method again
        productServicePaginable.getWithPagination(1, 5, new String[] { "productName", "asc" });

        // Verify that the method was called again due to cache eviction
        verify(productRepository, times(2)).findAll(pageRequest);
    }
}
