package br.com.lufecrx.demo.ecommerce.api.service.domain.wishlist;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import br.com.lufecrx.demo.ecommerce.api.model.dto.WishlistDTO;
import br.com.lufecrx.demo.ecommerce.api.repository.ProductRepository;
import br.com.lufecrx.demo.ecommerce.api.repository.WishlistRepository;
import br.com.lufecrx.demo.ecommerce.auth.model.User;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.product.ProductNotFoundException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistAlreadyExistsException;
import br.com.lufecrx.demo.ecommerce.exception.api.domain.wishlist.WishlistNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the service that is responsible for managing the wishlists.
 * 
 * 
 */
@Service
@Primary
@Qualifier("standard")
@Slf4j
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Add a product to a wishlist and save it to the database. 
     * The wishlist must belong to the authenticated user.
     * CacheEvict annotation is used to remove all entries from the cache when a product is added to a wishlist.
     * 
     * @param wishlistId The ID of the wishlist to add the product.
     * @param productId The ID of the product to add to the wishlist.
     * @throws WishlistNotFoundException WishlistNotFoundException is thrown if the wishlist does not exist.
     * @throws ProductNotFoundException ProductNotFoundException is thrown if the product does not exist.
     * 
     */
    @CacheEvict(value = "wishlists", allEntries = true)
    public void addProductToWishlist(Long wishlistId, Long productId) {
        log.info("Adding product with ID {} to wishlist with ID {}", productId, wishlistId);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Wishlist> wishlist = wishlistRepository.findByIdAndUser(productId, user);
        if (wishlist.isEmpty()) {
            throw new WishlistNotFoundException(wishlistId);
        }

        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }

        wishlist.get().addToWishlist(product.get());
        wishlistRepository.save(wishlist.get());
    }

    /**
     * Remove a product from a wishlist and save it to the database.
     * The wishlist must belong to the authenticated user.
     * CacheEvict annotation is used to remove all entries from the cache when a product is removed from a wishlist.
     * 
     * @param wishlistId The ID of the wishlist to remove the product.
     * @param productId The ID of the product to remove from the wishlist.
     * @throws WishlistNotFoundException WishlistNotFoundException is thrown if the wishlist does not exist.
     * @throws ProductNotFoundException ProductNotFoundException is thrown if the product does not exist.
     * 
     */
    @CacheEvict(value = "wishlists", allEntries = true)
    public void removeProductFromWishlist(Long wishlistId, Long productId) {
        log.info("Removing product with ID {} from wishlist with ID {}", productId, wishlistId);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Wishlist> wishlist = wishlistRepository.findByIdAndUser(productId, user);
        if (wishlist.isEmpty()) {
            throw new WishlistNotFoundException(wishlistId);
        }

        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }

        wishlist.get().removeFromWishlist(product.get());
        wishlistRepository.save(wishlist.get());
    }

    /**
     * Delete a wishlist by its ID.
     * The wishlist must belong to the authenticated user.
     * CacheEvict annotation is used to remove all entries from the cache when a wishlist is deleted.
     * 
     * @param wishlistId The ID of the wishlist to delete.
     * @throws WishlistNotFoundException WishlistNotFoundException is thrown if the wishlist does not exist.
     * 
     */
    @CacheEvict(value = "wishlists", allEntries = true)
    public void deleteWishlist(Long wishlistId) {
        log.info("Deleting wishlist with ID {}", wishlistId);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Wishlist> wishlist = wishlistRepository.findByIdAndUser(wishlistId, user);
        if (wishlist.isPresent()) {
            wishlistRepository.delete(wishlist.get());
        } else {
            throw new WishlistNotFoundException(wishlistId);
        }

    }

    /**
     * Get a wishlist by its ID.
     * The wishlist must belong to the authenticated user.
     * Cacheable annotation is used to cache the result of this method, so that the next time it is called with the same parameters, the result is returned from the cache.
     * 
     * @param wishlistId The ID of the wishlist to retrieve.
     * @throws WishlistNotFoundException If the wishlist does not exist, the exception WishlistNotFoundException is thrown.
     * @return The wishlist with the given ID
     * 
     */
    @Cacheable(value = "wishlists", key = "#wishlistId")
    public Optional<WishlistDTO> getWishlistById(Long wishlistId) {
        log.info("Getting wishlist by ID {}", wishlistId);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Wishlist> wishlist = wishlistRepository.findByIdAndUser(wishlistId, user);
        if (wishlist.isPresent()) {
            return Optional.of(WishlistDTO.from(wishlist.get()));
        }

        throw new WishlistNotFoundException(wishlistId);
    }

    /**
     * Get a wishlist by its name.
     * The wishlist must belong to the authenticated user.
     * Cacheable annotation is used to cache the result of this method, so that the next time it is called with the same parameters, the result is returned from the cache.
     * 
     * @param name the name of the wishlist to retrieve
     * @throws WishlistNotFoundException If the wishlist does not exist, the exception WishlistNotFoundException is thrown.
     * @return the wishlist with the given name
     * 
     */
    @Cacheable(value = "wishlists", key = "#name")
    public Optional<WishlistDTO> getWishlistByName(String name) {
        log.info("Getting wishlist by name {}", name);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Wishlist> wishlist = wishlistRepository.findByNameAndUser(name, user);

        if (wishlist.isPresent()) {
            return Optional.of(WishlistDTO.from(wishlist.get()));
        }

        throw new WishlistNotFoundException(name);
    }

    /**
     * Create a new wishlist and save it to the database.
     * The wishlist must belong to the authenticated user.
     * CacheEvict annotation is used to remove all entries from the cache when a new wishlist is created.
     * 
     * @param wishlist the data of the new wishlist
     * @throws WishlistAlreadyExistsException If a wishlist with the same name already exists, the exception WishlistAlreadyExistsException is thrown.
     * 
     */
    @CacheEvict(value = "wishlists", allEntries = true)
    public void createWishlist(WishlistDTO wishlist) {
        log.info("Creating wishlist with name {}", wishlist.name());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Verify if the authenticated user already has a wishlist with the same name
        if (wishlistRepository.existsByNameAndUser(wishlist.name(), user)) {
            throw new WishlistAlreadyExistsException(wishlist.name());
        }

        Wishlist newWishlist = Wishlist.builder()
                .name(wishlist.name())
                .build();
        newWishlist.setUser(user);

        wishlistRepository.save(newWishlist);
    }

    /**
     * Rename a wishlist by its ID with the new data and save it to the database.
     * The wishlist must belong to the authenticated user.
     * 
     * @param wishlistId the ID of the wishlist to rename
     * @param updatedWishlist the new data of the wishlist
     * @throws WishlistNotFoundException If the wishlist does not exist, the exception WishlistNotFoundException is thrown.
     * @throws WishlistAlreadyExistsException If a wishlist with the same name already exists, the exception WishlistAlreadyExistsException is thrown.
     * 
     */
    @CacheEvict(value = "wishlists", allEntries = true)
    public void renameWishlist(Long wishlistId, WishlistDTO updatedWishlist) {
        log.info("Updating wishlist with ID {}", wishlistId);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Wishlist> wishlist = wishlistRepository.findByIdAndUser(wishlistId, user);
        if (wishlist.isPresent()) {
            if (wishlistRepository.existsByNameAndUser(updatedWishlist.name(), user)) {
                throw new WishlistAlreadyExistsException(updatedWishlist.name());
            }
            wishlist.get().setName(updatedWishlist.name());
            wishlistRepository.save(wishlist.get());
        } else {
            throw new WishlistNotFoundException(wishlistId);
        }

    }
}
