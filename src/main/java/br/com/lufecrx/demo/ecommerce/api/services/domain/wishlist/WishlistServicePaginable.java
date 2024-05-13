package br.com.lufecrx.demo.ecommerce.api.services.domain.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import br.com.lufecrx.demo.ecommerce.api.repository.WishlistRepository;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistsEmptyException;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is a paginable version of the WishlistService class, responsible for managing the wishlists with pagination.
 * 
 * @see WishlistService
 * 
 */
@Service
@Qualifier("paginable")
@Slf4j
public class WishlistServicePaginable {
    
    @Autowired
    private WishlistRepository wishlistRepository;

    /**
     * Retrieve all wishlists with pagination. If the wishlists list is empty, the exception WishlistsEmptyException is thrown.
     * Cacheable annotation is used to cache the result of this method, so that the next time it is called with the same parameters, the result is returned from the cache.
     * 
     * @param page the page number
     * @param size the number of elements per page
     * @param sort the sorting criteria (property and direction)
     * @return the wishlists list with pagination
     */
    @Cacheable(value = "wishlists", key = "#page.toString() + #size.toString() + T(java.util.Arrays).toString(#sort)")
    public Iterable<Wishlist> getWithPagination(int page, int size, String[] sort) {
        log.info("Getting all wishlists with pagination, page {} and size {}", page, size);

        String property = sort[0];
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);

        Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, property));
        
        Page<Wishlist> wishlistPage = wishlistRepository.findAll(pageRequest);

        if (!wishlistPage.iterator().hasNext()) {
            throw new WishlistsEmptyException();
        }
    
        return wishlistPage;
    }
}
