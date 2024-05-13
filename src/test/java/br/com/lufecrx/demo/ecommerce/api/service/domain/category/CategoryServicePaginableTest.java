package br.com.lufecrx.demo.ecommerce.api.service.domain.category;

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

import br.com.lufecrx.demo.ecommerce.api.model.Category;
import br.com.lufecrx.demo.ecommerce.api.repository.CategoryRepository;
import br.com.lufecrx.demo.ecommerce.api.services.domain.category.CategoryServicePaginable;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoriesEmptyException;

public class CategoryServicePaginableTest {

    @InjectMocks
    private CategoryServicePaginable categoryServicePaginable;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWithPaginationReturnsNonEmptyPage() {
        Category category = new Category();
        Page<Category> page = new PageImpl<>(Arrays.asList(category));

        // Mocking the behavior of the methods to simulate a non-empty page
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Getting the categories
        Iterable<Category> categories = categoryServicePaginable.getWithPagination(0, 1, new String[] { "id", "asc" });

        // Verifying if the methods were called correctly
        assertTrue(categories.iterator().hasNext());
        assertEquals(category, categories.iterator().next());
    }

    @Test
    public void testGetWithPaginationReturnsEmptyPage() {
        Page<Category> page = Page.empty();

        // mock the behavior of the methods to simulate an empty page
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Getting the categories
        assertThrows(CategoriesEmptyException.class, () -> {
            categoryServicePaginable.getWithPagination(0, 1, new String[] { "id", "asc" });
        });
    }

    @Test
    public void testGetWithPaginationThrowsException() {
        // Mocking the behavior of the methods to simulate when an exception is thrown
        when(categoryRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException());

        // Getting the categories and expecting an exception
        assertThrows(RuntimeException.class, () -> {
            categoryServicePaginable.getWithPagination(0, 1, new String[] { "id", "asc" });
        });
    }
}