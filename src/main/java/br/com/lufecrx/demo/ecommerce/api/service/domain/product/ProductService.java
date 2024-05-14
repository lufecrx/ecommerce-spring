package br.com.lufecrx.demo.ecommerce.api.service.domain.product;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import br.com.lufecrx.demo.ecommerce.api.model.Category;
import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.model.dto.CategoryDTO;
import br.com.lufecrx.demo.ecommerce.api.model.dto.ProductDTO;
import br.com.lufecrx.demo.ecommerce.api.repository.CategoryRepository;
import br.com.lufecrx.demo.ecommerce.api.repository.ProductRepository;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.product.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the service that is responsible for managing the products.
 * 
 */
@Service
@Primary
@Qualifier("standard")
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Create a new product with the given data and save it to the database.
     * The method validateCategories is used to verify if the categories already exist in the database.
     * CacheEvict annotation is used to remove all entries from the cache when a new product is created.
     * 
     * @param product the data of the new product
     * @see validateCategories
     * 
     */
    @CacheEvict(value = "products", allEntries = true)
    public void createProduct(ProductDTO product) {
        log.info("Creating product with name {}", product.name());

        Optional<Set<Category>> categories = validateCategories(product, categoryRepository);

        Product newProduct = Product.builder()
                .productName(product.name())
                .price(product.price())
                .categories(categories.orElse(null))
                .build();

        productRepository.save(newProduct);
    }

    /**
     * Retrieve a specific product by its ID. 
     * Cacheable annotation is used to cache the result of this method, so that the next time it is called with the same parameters, the result is returned from the cache.
     * 
     * @param productId the ID of the product to retrieve
     * @throws ProductNotFoundException If the product does not exist, the exception ProductNotFoundException is thrown.
     * @return the product with the given ID
     */
    @Cacheable(value = "products", key = "#productId")
    public Optional<ProductDTO> getProductById(Long productId) {
        log.info("Getting product by ID {}", productId);

        Optional<Product> product = productRepository.findById(productId);
        
        // Return the product if it exists, otherwise throw an exception
        if (product.isPresent()) {
            return Optional.of(ProductDTO.from(product.get()));
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    /**
     * Update the product with the given ID with the new data. 
     * CacheEvict annotation is used to remove all entries from the cache when a product is updated.
     * 
     * @param productId the ID of the product to update
     * @param updatedProduct the new data of the product
     * @throws ProductNotFoundException If the product does not exist, the exception ProductNotFoundException is thrown.
     * 
     */
    @CacheEvict(value = "products", allEntries = true)
    public void updateProduct(Long productId, ProductDTO updatedProduct) {
        log.info("Updating product with ID {}", productId);

        Optional<Set<Category>> categories = validateCategories(updatedProduct, categoryRepository);

        Optional<Product> existingProduct = productRepository.findById(productId);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();

            product.setProductName(updatedProduct.name());
            product.setPrice(updatedProduct.price());
            product.setCategories(categories.orElse(null));

            productRepository.save(product);
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    /**
     * Delete the product with the given ID.
     * CacheEvict annotation is used to remove all entries from the cache when a product is deleted.
     * 
     * @param productId the ID of the product to delete
     * @throws ProductNotFoundException If the product does not exist, the exception ProductNotFoundException is thrown.
     * 
     */
    @CacheEvict(value = "products", allEntries = true)
    public void deleteProduct(Long productId) {
        log.info("Deleting product with ID {}", productId);

        Optional<Product> existingProduct = productRepository.findById(productId);

        if (existingProduct.isPresent()) {
            productRepository.delete(existingProduct.get());
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    /**
     * Validate the categories of a product. 
     * If a category already exists in the database, it is added to the list of existing categories.
     * If a category does not exist, it is saved to the database and added to the list of existing categories.
     * 
     * @param product the product to validate
     * @param categoryRepository the repository for the Category entity
     * @return the list of existing categories
     * 
     */
    public Optional<Set<Category>> validateCategories(ProductDTO product, CategoryRepository categoryRepository) {
        log.info("Validating categories for product with name {}", product.name());

        Set<Category> existingCategories = new HashSet<>();

        for (CategoryDTO categoryDTO : product.categories()) {
            Optional<Category> existingCategory = categoryRepository.findByName(categoryDTO.name());

            if (existingCategory.isPresent()) {
                // If the category already exists, add it to the list of existing categories
                existingCategories.add(existingCategory.get());
            } else {
                // If the category doesn't exist, save it to the database and add it to the list of existing categories
                Category category = Category.builder()
                                .name(categoryDTO.name())
                                .build();

                Category savedCategory = categoryRepository.save(category);
                existingCategories.add(savedCategory);
            }
        }
        // Return the list of existing categories
        return Optional.ofNullable(existingCategories.isEmpty() ? null : existingCategories);
    }
}
