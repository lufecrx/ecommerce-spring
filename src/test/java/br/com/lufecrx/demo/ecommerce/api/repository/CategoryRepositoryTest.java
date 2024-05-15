package br.com.lufecrx.demo.ecommerce.api.repository;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.github.javafaker.Faker;

import br.com.lufecrx.demo.ecommerce.api.model.Category;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    private Faker faker = new Faker();

    @BeforeEach
    public void setUp() {
        entityManager.clear();
        category = Category.builder().name(faker.commerce().department()).build();
    }

    @Test
    public void whenFindByName_thenReturnCategory() {
        Category savedCategory = entityManager.persistAndFlush(category);

        Optional<Category> foundCategory = categoryRepository.findByName(savedCategory.getName());

        assertTrue(foundCategory.isPresent());
        assertTrue((foundCategory.get().getName()).equals(savedCategory.getName()));
    }

    @Test
    public void whenNotFindByName_thenReturnEmpty() {
        Optional<Category> foundCategory = categoryRepository.findByName(category.getName());

        assertTrue(foundCategory.isEmpty());
    }

    @Test
    public void whenExistsByName_thenReturnTrue() {
        Category savedCategory = entityManager.persistAndFlush(category);

        boolean exists = categoryRepository.existsByName(savedCategory.getName());

        assertTrue(exists);
    }

    @Test
    public void whenNotExistsByName_thenReturnFalse() {
        boolean exists = categoryRepository.existsByName(category.getName());

        assertTrue(!exists);
    }
}