package br.com.lufecrx.demo.ecommerce.api.services.domain.category;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import br.com.lufecrx.demo.ecommerce.api.model.Category;
import br.com.lufecrx.demo.ecommerce.api.model.dto.CategoryDTO;
import br.com.lufecrx.demo.ecommerce.api.repository.CategoryRepository;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoryAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoryNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the service that is responsible for managing the categories.
 * 
 */
@Service
@Primary
@Qualifier("standard")
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Create a new category with the given data and save it to the database. 
     * If a category with the same name already exists, the exception CategoryAlreadyExistsException is thrown.
     * CacheEvict annotation is used to remove all entries from the cache when a new category is created.
     * 
     * @param category the data of the new category
     * 
     */
    @CacheEvict(value = "categories", allEntries = true)
    public void createCategory(CategoryDTO category) {
        log.info("Creating category with name {}", category.name());

        if (categoryRepository.existsByName(category.name())) {
            throw new CategoryAlreadyExistsException(category.name());
        }

        Category newCategory = Category.builder()
                .name(category.name())
                .build();

        categoryRepository.save(newCategory);
    }

    /**
     * Retrieve a specific category by its ID. 
     * If the category does not exist, the exception CategoryNotFoundException is thrown.
     * Cacheable annotation is used to cache the result of this method, so that the next time it is called with the same parameters, the result is returned from the cache.
     * 
     * @param categoryId the ID of the category to retrieve
     * @return the category with the given ID
     * 
     */
    @Cacheable(value = "categories", key = "#categoryId")
    public Optional<CategoryDTO> getCategoryById(Long categoryId) {
        log.info("Getting category by ID {}", categoryId);

        Optional<Category> category = categoryRepository.findById(categoryId);
        
        if (category.isPresent()) {
            return Optional.of(CategoryDTO.from(category.get()));
        } else {
            throw new CategoryNotFoundException(categoryId);
        }
    }

    /**
     * Rename the category with the given ID with the new data. 
     * If the category does not exist, the exception CategoryNotFoundException is thrown.
     * CacheEvict annotation is used to remove all entries from the cache when a category is renamed.
     * 
     * @param categoryId the ID of the category to rename
     * @param updatedCategory the new data of the category
     * 
     */
    @CacheEvict(value = "categories", allEntries = true)
    public void renameCategory(Long categoryId, CategoryDTO updatedCategory) {
        log.info("Updating category with ID {}", categoryId);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        category.setName(updatedCategory.name());

        categoryRepository.save(category);
    }

    /**
     * Delete the category with the given ID.
     * If the category does not exist, the exception CategoryNotFoundException is thrown.
     * CacheEvict annotation is used to remove all entries from the cache when a category is deleted.
     * 
     * @param categoryId the ID of the category to delete
     */
    @CacheEvict(value = "categories", allEntries = true)
    public void deleteCategory(Long categoryId) {
        log.info("Deleting category with ID {}", categoryId);

        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException(categoryId);
        }

        categoryRepository.deleteById(categoryId);
    }

}
