package br.com.lufecrx.demo.ecommerce.api.controller.domain.wishlist;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import br.com.lufecrx.demo.ecommerce.api.service.domain.wishlist.WishlistServicePaginable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The WishlistControllerPaginable class is responsible for handling the HTTP requests related to products with pagination.
 * It uses the WishlistServicePaginable to perform operations on the database. 
 * 
 * @see WishlistServicePaginable
 * @see Wishlist
 */
@RestController
@RequestMapping("/wishlists/paginable")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Wishlists found"),
    @ApiResponse(responseCode = "400", description = "Invalid arguments to pagination"),
    @ApiResponse(responseCode = "404", description = "Wishlists not found")
})
public class WishlistControllerPaginable {

    private final WishlistServicePaginable wishlistService;

    public WishlistControllerPaginable(@Qualifier("paginable") WishlistServicePaginable wishlistService) {
        this.wishlistService = wishlistService;
    }

    /**
     * This method returns all wishlists with pagination, sorting the results by the given fields.
     * @param page The page number to be returned.
     * @param size The number of elements per page. Note: the default value is 10 and the maximum value is 10.
     * @param sort An array of strings with the format "field,direction" to sort the results. Note: the default value is "name,asc".
     * @return The list of wishlists found.
     */
    @Operation(summary = "Find all wishlists with pagination", 
            description = "Find all wishlists with pagination. Note: Maximum size is 10.")
    @GetMapping()
    public ResponseEntity<Iterable<Wishlist>> findAll(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(value = "sort", defaultValue = "name,asc") String[] sort) {
        Iterable<Wishlist> entities = wishlistService.getWithPagination(page, size, sort);
        return ResponseEntity.ok(entities);
    }

}