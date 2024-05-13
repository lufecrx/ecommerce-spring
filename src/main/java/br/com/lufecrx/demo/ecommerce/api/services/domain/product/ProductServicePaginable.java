package br.com.lufecrx.demo.ecommerce.api.services.domain.product;

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
     * Retrieve all products with pagination. If the products list is empty, the exception ProductsEmptyException is thrown.
     * Cacheable annotation is used to cache the result of this method, so that the next time it is called with the same parameters, the result is returned from the cache.
     * 
     * @param page the page number
     * @param size the number of elements per page
     * @param sort the sorting criteria (property and direction)
     * @return the products list with pagination
     * 
     */
    @Cacheable(value = "products", key = "#page.toString() + #size.toString() + T(java.util.Arrays).toString(#sort)")
    public Iterable<Product> getWithPagination(int page, int size, String[] sort) {
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