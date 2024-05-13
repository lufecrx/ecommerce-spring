package br.com.lufecrx.demo.ecommerce.api.service.domain.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.lufecrx.demo.ecommerce.api.model.Category;
import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.model.dto.CategoryDTO;
import br.com.lufecrx.demo.ecommerce.api.model.dto.ProductDTO;
import br.com.lufecrx.demo.ecommerce.api.repository.CategoryRepository;
import br.com.lufecrx.demo.ecommerce.api.repository.ProductRepository;
import br.com.lufecrx.demo.ecommerce.api.services.domain.product.ProductService;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.product.ProductNotFoundException;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO("Test Product", 100.0,
                Collections.singleton(new CategoryDTO("Test Category")));
        Category category = new Category();

        // Mocking the findByName method to return a category
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

        // Creating the product
        productService.createProduct(productDTO);

        // Verifying if the methods were called
        verify(productRepository, times(1)).save(any(Product.class));
        verify(categoryRepository, times(1)).findByName(anyString());
    }

    @Test
    public void testGetProductWhenProductExists() {
        Long productId = 1L;
        Product product = new Product();

        // Mocking the findById method to return a product
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Getting the product
        productService.getProductById(productId);

        // Verifying if the findById method was called once
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testGetProductWhenProductDoesNotExist() {
        Long productId = 1L;

        // Mocking the findById method to return an empty optional
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Getting the product and expecting an exception
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));

        // Verifying if the findById method was called once
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testUpdateProductWhenProductExists() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO("Test Product", 100.0,
                Collections.singleton(new CategoryDTO("Test Category")));
        Product product = new Product();
        Category category = new Category();

        // Mocking the findById method to return a product and the findByName method to
        // return a category
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

        // Updating the product
        productService.updateProduct(productId, productDTO);

        // Verifying if the methods were called
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productRepository, times(1)).findById(productId);
        verify(categoryRepository, times(1)).findByName(anyString());

        // Asserting if the product was updated correctly
        assert product.getProductName().equals(productDTO.name());
        assert product.getPrice().equals(productDTO.price());
        assert product.getCategories().equals(Collections.singleton(category));
    }

    @Test
    public void testUpdateProductWhenProductDoesNotExist() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO("Test Product", 100.0,
                Collections.singleton(new CategoryDTO("Test Category")));

        // Mocking the findById method to return an empty optional
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Updating the product and expecting an exception
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, productDTO));

        // Verifying if the findById method was called once and the save method was
        // never called
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void testDeleteProductWhenProductExists() {
        Long productId = 1L;
        Product product = new Product();

        // Mocking the findById method to return a product
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Deleting the product
        productService.deleteProduct(productId);

        // Verifying if the delete method was called once and the findById method was
        // called once
        verify(productRepository, times(1)).delete(product);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testValidateCategories() {
        Set<CategoryDTO> categoryDTOs = new HashSet<>();

        // Creating the category DTOs with two existing categories and one new category
        categoryDTOs.add(new CategoryDTO("Existing Category"));
        categoryDTOs.add(new CategoryDTO("Existing Category"));
        categoryDTOs.add(new CategoryDTO("New Category"));
    
        ProductDTO productDTO = new ProductDTO("Test Product", 100.0, categoryDTOs);
    
        Category existingCategory = new Category(1L, "Existing Category");
        Category newCategory = new Category(2L, "New Category");
    
        // Mocking the behavior of the category repository in the validateCategories method
        when(categoryRepository.findByName("Existing Category")).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findByName("New Category")).thenReturn(Optional.empty());
    
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);
    
        // Validating the categories
        Optional<Set<Category>> categories = productService.validateCategories(productDTO, categoryRepository);

        // Verifying if the methods were called and if the categories were validated, not containing duplicates (categories with the same name)
        verify(categoryRepository, times(1)).findByName("Existing Category");
        verify(categoryRepository, times(1)).findByName("New Category");
        verify(categoryRepository, times(1)).save(any(Category.class));
        assertTrue(categories.isPresent());
        assertEquals(2, categories.get().size());
        assertTrue(categories.get().contains(existingCategory));
        assertTrue(categories.get().contains(newCategory));
    }
}
