package br.com.lufecrx.demo.ecommerce.api.service.domain.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.repository.ProductRepository;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.product.ProductsEmptyException;
import br.com.lufecrx.demo.ecommerce.exception.api.pagination.InvalidArgumentsToPaginationException;
import br.com.lufecrx.demo.ecommerce.exception.api.pagination.InvalidSortDirectionException;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is a paginable version of the ProductService class, responsible for managing the products with pagination.
 * 
 * @see ProductService
 * 
 */
@Service
@Qualifier("paginable")
@Slf4j
public class ProductServicePaginable extends ProductService {

    @Autowired
    private ProductRepository productRepository;


    /**
     * Retrieve products with pagination.
     * Cacheable annotation is used to cache the result of this method, so that the next time it is called with the same parameters, the result is returned from the cache.
     * 
     * @param page the page number
     * @param size the number of elements per page
     * @param sort the sorting criteria (property and direction)
     * @throws InvalidArgumentsToPaginationException If the page or size are negative, the exception InvalidArgumentsToPagination is thrown.
     * @throws InvalidSortDirectionException If the sorting direction is invalid (not "asc" or "desc"), the exception InvalidSortDirectionException is thrown.
     * @throws ProductsEmptyException If there are no products in the database, the exception ProductsEmptyException is thrown.
     * @return the products list with pagination
     * 
     */
    @Cacheable(value = "products", key = "#page.toString() + #size.toString() + T(java.util.Arrays).toString(#sort)")
    public Iterable<Product> getWithPagination(int page, int size, String[] sort) {

        if (page < 0 || size < 0) {
            throw new InvalidArgumentsToPaginationException();
        }

        if (sort.length != 2 || (!sort[1].equalsIgnoreCase("asc") && !sort[1].equalsIgnoreCase("desc"))) {
            throw new InvalidSortDirectionException();
        }

        // If the size is greater than 60, set it to 60
        if (size > 60) {
            size = 60;
        }

        log.info("Getting all products with pagination, page {} and size {}", page, size);

        String property = sort[0];
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);

        Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, property));

        Page<Product> productPage = productRepository.findAll(pageRequest);

        if (!productPage.iterator().hasNext()) {
            throw new ProductsEmptyException();
        }

        return productPage;
    }
}