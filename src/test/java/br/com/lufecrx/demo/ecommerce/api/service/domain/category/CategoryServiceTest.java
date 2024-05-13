package br.com.lufecrx.demo.ecommerce.api.service.domain.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.lufecrx.demo.ecommerce.api.model.Category;
import br.com.lufecrx.demo.ecommerce.api.model.dto.CategoryDTO;
import br.com.lufecrx.demo.ecommerce.api.repository.CategoryRepository;
import br.com.lufecrx.demo.ecommerce.api.services.domain.category.CategoryService;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoryAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoryNotFoundException;

public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategoryWhenCategoryDoesNotExist() {
        CategoryDTO categoryDTO = new CategoryDTO("Test Category");

        // Mocking the behavior of the methods to simulate a save operation of a new category
        when(categoryRepository.existsByName(anyString())).thenReturn(false);

        // Creating the category
        categoryService.createCategory(categoryDTO);

        // Verifying if the methods were called
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testCreateCategoryWhenCategoryExists() {
        CategoryDTO categoryDTO = new CategoryDTO("Test Category");

        // Mocking the behavior of the methods to simulate a save operation of an existing category
        when(categoryRepository.existsByName(anyString())).thenReturn(true);

        // Creating the category and expecting an exception
        try {
            categoryService.createCategory(categoryDTO);
        } catch (CategoryAlreadyExistsException e) {
            verify(categoryRepository, times(0)).save(any(Category.class));
        }
    }

    @Test
    public void testGetCategoryByIdWhenCategoryExists() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        // Mocking the behavior of the methods to simulate a find operation of a category by ID that exists
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        // Getting the category by ID
        Optional<CategoryDTO> categoryDTO = categoryService.getCategoryById(1L);

        // Verifying if the methods were called and the result
        assertTrue(categoryDTO.isPresent());
        assertEquals(categoryDTO.get().name(), category.getName());
    }

    @Test
    public void testGetCategoryByIdWhenCategoryDoesNotExist() {
        // Mocking the behavior of the methods to simulate a find operation of a category by ID that does not exist
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Getting the category by ID and expecting an exception
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.getCategoryById(1L);
        });
    }

    @Test
    public void testUpdateCategoryWhenCategoryExists() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        CategoryDTO updatedCategory = new CategoryDTO("Updated Category");

        // Mocking the behavior of the methods to simulate an update operation of a category that exists
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        // Updating the category
        categoryService.renameCategory(1L, updatedCategory);

        // Verifying if the methods were called
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testUpdateCategoryWhenCategoryDoesNotExist() {
        CategoryDTO updatedCategory = new CategoryDTO("Updated Category");

        // Mocking the behavior of the methods to simulate an update operation of a category that does not exist
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Updating the category and expecting an exception
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.renameCategory(1L, updatedCategory);
        });
    }

    @Test
    public void testDeleteCategoryWhenCategoryExists() {
        Long categoryId = 1L;

        // Mocking the behavior of the methods to simulate a delete operation of a category that exists
        when(categoryRepository.existsById(anyLong())).thenReturn(true);

        // Deleting the category
        categoryService.deleteCategory(categoryId);

        // Verifying if the methods were called
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    public void testDeleteCategoryWhenCategoryDoesNotExist() {
        Long categoryId = 1L;

        // Mocking the behavior of the methods to simulate a delete operation of a category that does not exist
        when(categoryRepository.existsById(anyLong())).thenReturn(false);

        // Deleting the category and expecting an exception
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.deleteCategory(categoryId);
        });
    }
}