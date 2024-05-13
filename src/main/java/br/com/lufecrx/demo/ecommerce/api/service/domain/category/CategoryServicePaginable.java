package br.com.lufecrx.demo.ecommerce.api.service.domain.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.lufecrx.demo.ecommerce.api.model.Category;
import br.com.lufecrx.demo.ecommerce.api.repository.CategoryRepository;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.category.CategoriesEmptyException;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is a paginable version of the CategoryService class, responsible for managing the categories with pagination.
 * 
 * @see CategoryService
 * 
 */
@Service
@Slf4j
@Qualifier("paginable")
public class CategoryServicePaginable extends CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Retrieve all categories with pagination. If the categories list is empty, the exception CategoriesEmptyException is thrown.
     * Cacheable annotation is used to cache the result of this method, so that the next time it is called with the same parameters, the result is returned from the cache.
     * 
     * @param page the page number
     * @param size the number of elements per page
     * @param sort the sorting criteria (property and direction)
     * @return the categories list with pagination
     * 
     */
    @Cacheable(value = "categories", key = "#page.toString() + #size.toString() + T(java.util.Arrays).toString(#sort)")
    public Iterable<Category> getWithPagination(int page, int size, String[] sort) {
        log.info("Getting all categories with pagination, page {}, size {} and sort {}", page, size, sort);

        String property = sort[0];
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);

        Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, property));

        Page<Category> categoryPage = categoryRepository.findAll(pageRequest);

        if (!categoryPage.iterator().hasNext()) {
            throw new CategoriesEmptyException();
        }

        return categoryPage;
    }

}