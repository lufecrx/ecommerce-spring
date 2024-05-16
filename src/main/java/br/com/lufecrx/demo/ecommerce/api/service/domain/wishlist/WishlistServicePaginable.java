package br.com.lufecrx.demo.ecommerce.api.service.domain.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import br.com.lufecrx.demo.ecommerce.api.repository.WishlistRepository;
import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.pagination.InvalidArgumentsToPaginationException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.pagination.InvalidSortDirectionException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistsEmptyException;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is a paginable version of the WishlistService class, responsible for managing the wishlists with pagination.~
 * To manage the wishlists, the user must be authenticated and the wishlist must belong to the authenticated user.
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
     * Retrieve wishlists with pagination.
     * The user must be authenticated to access this method and the wishlists must belong to the authenticated user.
     * Cacheable annotation is used to cache the result of this method, so that the next time it is called with the same parameters, the result is returned from the cache.
     * 
     * @param page the page number
     * @param size the number of elements per page
     * @param sort the sorting criteria (property and direction)
     * @throws InvalidArgumentsToPaginationException If the page or size are negative, the exception InvalidArgumentsToPagination is thrown.
     * @throws InvalidSortDirectionException If the sorting direction is invalid (not "asc" or "desc"), the exception InvalidSortDirectionException is thrown.
     * @throws WishlistsEmptyException If there are no wishlists in the database, the exception WishlistsEmptyException is thrown.
     * @return the wishlists list with pagination
     * 
     */
    @Cacheable(value = "wishlists", key = "#page.toString() + #size.toString() + T(java.util.Arrays).toString(#sort)")
    public Iterable<Wishlist> getWithPagination(int page, int size, String[] sort) {
        
        log.info("Getting all wishlists with pagination, page {} and size {}", page, size);
        
        if (page < 0 || size < 0) {
            throw new InvalidArgumentsToPaginationException();
        }

        // If the size is greater than 10, set it to 10
        if (size > 10) {
            size = 10;
        }
        
        if (sort.length != 2 || (!sort[1].equalsIgnoreCase("asc") && !sort[1].equalsIgnoreCase("desc"))) {
            throw new InvalidSortDirectionException();
        }

        String property = sort[0];
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, property));
        Page<Wishlist> wishlistPage = wishlistRepository.findAllByUser(user, pageRequest);
        
        if (!wishlistPage.iterator().hasNext()) {
            throw new WishlistsEmptyException();
        }
    
        return wishlistPage;
    }
}
