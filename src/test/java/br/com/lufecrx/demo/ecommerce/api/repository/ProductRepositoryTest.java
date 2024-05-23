package br.com.lufecrx.demo.ecommerce.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.api.model.Category;
import br.com.lufecrx.demo.ecommerce.api.model.Product;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        Product product1 = new Product();

        Category category1 = new Category();
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setName("Computers");

        Category category3 = new Category();
        category3.setName("Smartphones");


        product1.setProductName("Laptop");
        product1.setCategories(new HashSet<>(Arrays.asList(category1, category2)));
        product1.setPrice(1200.00);

        Product product2 = new Product();
        product2.setProductName("Smartphone");
        product2.setCategories(new HashSet<>(Arrays.asList(category1, category3)));
        product2.setPrice(800.00);

        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    public void whenFindByNameAndCategoryAndPriceRange_thenReturnProducts() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Optional<Page<Product>> foundProducts = productRepository.findByNameAndCategoryAndPriceRange("Laptop", "Electronics", 1000.00, 1500.00, pageRequest);

        assertThat(foundProducts).isPresent();
        assertThat(foundProducts.get().getContent()).hasSize(1);
        assertThat(foundProducts.get().getContent().get(0).getProductName()).isEqualTo("Laptop");
    }

    @Test
    public void whenFindByNonExistingNameAndCategoryAndPriceRange_thenReturnEmpty() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Optional<Page<Product>> foundProducts = productRepository.findByNameAndCategoryAndPriceRange("Tablet", "Electronics", 500.00, 900.00, pageRequest);

        assertThat(foundProducts).isPresent();
        assertThat(foundProducts.get().getContent()).isEmpty();    }
}