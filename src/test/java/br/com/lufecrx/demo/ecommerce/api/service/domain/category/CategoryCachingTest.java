package br.com.lufecrx.demo.ecommerce.api.service.domain.category;

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

import br.com.lufecrx.demo.ecommerce.api.model.Category;
import br.com.lufecrx.demo.ecommerce.api.repository.CategoryRepository;
import br.com.lufecrx.demo.ecommerce.api.services.domain.category.CategoryService;
import br.com.lufecrx.demo.ecommerce.api.services.domain.category.CategoryServicePaginable;

@SpringBootTest
public class CategoryCachingTest {

    @Autowired
    private CategoryServicePaginable categoryServicePaginable;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CacheManager cacheManager;

    @SpyBean
    private CategoryRepository categoryRepository;

    private Faker faker;

    private List<Category> categories;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
        categories = fillCategories();
        categoryRepository.saveAll(categories);
    }

    @AfterEach
    public void tearDown() {
        categoryRepository.deleteAll();
        cacheManager.getCache("categories").clear();
    }

    public List<Category> fillCategories() {
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Category category = new Category();
            String name = faker.commerce().department();
            category.setName(name);

            categories.add(category);
        }

        return categories;
    }

    @Test
    public void testCaching() {
        // Call the method 100 times
        for (int i = 0; i < 100; i++) {
            categoryServicePaginable.getWithPagination(1, 5, new String[] { "name", "asc" });
        }

        PageRequest pageRequest = PageRequest.of(1, 5, Sort.by("name").ascending());

        // Verify that the method was called only once due to caching
        verify(categoryRepository, times(1)).findAll(pageRequest);
    }

    @Test
    public void testCacheEvict() {
        // Call the method 100 times
        for (int i = 0; i < 100; i++) {
            categoryServicePaginable.getWithPagination(1, 5, new String[] { "name", "asc" });
        }

        PageRequest pageRequest = PageRequest.of(1, 5, Sort.by("name").ascending());

        // Verify that the method was called only once due to caching
        verify(categoryRepository, times(1)).findAll(pageRequest);

        // Delete a wishlist to evict the cache
        categoryService.deleteCategory(categories.get(0).getId());

        // Call the method again
        categoryServicePaginable.getWithPagination(1, 5, new String[] { "name", "asc" });

        // Verify that the method was called again due to cache eviction
        verify(categoryRepository, times(2)).findAll(pageRequest);
    }
}
