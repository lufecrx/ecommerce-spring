package br.com.lufecrx.demo.ecommerce.api.controller.domain.wishlist;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;
import br.com.lufecrx.demo.ecommerce.api.model.dto.WishlistDTO;
import br.com.lufecrx.demo.ecommerce.api.service.domain.wishlist.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 * The WishlistController class is responsible for handling the HTTP requests related to wishlists.
 * It uses the WishlistService to perform operations on the database.
 * 
 * @see WishlistService
 * @see Wishlist
 * 
 */
@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "You are not authorized to access this resource"),
        @ApiResponse(responseCode = "404", description = "Wishlist not found"),
        @ApiResponse(responseCode = "409", description = "Wishlist already exists"),
})
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(@Qualifier("standard") WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * This methods find a wishlist by its ID and returns it. 
     * @param wishlistId the ID of the wishlist to be found, passed as a request parameter.
     * @return The DTO of the wishlist found.
     */
    @Operation(summary = "Find a wishlist by its ID", description = "Find a wishlist by its ID")
    @ApiResponse(responseCode = "200", description = "Wishlist found by its ID")
    @GetMapping("/find")
    public ResponseEntity<Optional<WishlistDTO>> findById(
            @RequestParam(name = "wishlist") Long wishlistId) {

        Optional<WishlistDTO> entity = wishlistService.getWishlistById(wishlistId);
        return ResponseEntity.ok(entity);
    }

    /**
     * This methods returns a wishlist by its name.
     * @param name the name of the wishlist to be found, passed as a request parameter.
     * @return The DTO of the wishlist found.
     */
    @Operation(summary = "Find a wishlist by its name", description = "Find a wishlist by its name")
    @ApiResponse(responseCode = "200", description = "Wishlist found by its name")
    @GetMapping("/find-by-name")
    public ResponseEntity<Optional<WishlistDTO>> findByName(
            @RequestParam(name = "name") String name) {

        Optional<WishlistDTO> entity = wishlistService.getWishlistByName(name);
        return ResponseEntity.ok(entity);
    }

    /**
     * This methods create a new wishlist with the given data.
     * @param wishlistDTO the DTO of the wishlist to be created, passed as a request body. 
     * @return A message indicating that the wishlist was created.
     */
    @Operation(summary = "Create a new wishlist", description = "Create a new wishlist with the given data")
    @ApiResponse(responseCode = "200", description = "Wishlist created")
    @PostMapping("/add")
    public ResponseEntity<String> save(
            @RequestBody @Valid WishlistDTO wishlistDTO) {

        wishlistService.createWishlist(wishlistDTO);
        return ResponseEntity.ok(bundle.getString("wishlist.successfully_created"));
    }

    /**
     * This methods add a product to a wishlist.
     * @param wishlistId the ID of the wishlist to which the product will be added, passed as a request parameter. 
     * @param productId the ID of the product to be added to the wishlist, passed as a request parameter.
     * @return A message indicating that the product was added to the wishlist.
     */
    @Operation(summary = "Add a product to a wishlist", description = "Add a product to a wishlist with the given data")
    @ApiResponse(responseCode = "200", description = "Product added to wishlist")
    @PutMapping("/add-product")
    public ResponseEntity<String> addProduct(
            @RequestParam(name = "wishlist") Long wishlistId, 
            @RequestParam(name = "product") Long productId) {
        
        wishlistService.addProductToWishlist(wishlistId, productId);
        return ResponseEntity.ok(bundle.getString("wishlist.successfully_added_product"));
    }

    /**
     * This methods remove a product from a wishlist.
     * @param wishlistId the ID of the wishlist from which the product will be removed, passed as a request parameter.
     * @param productId the ID of the product to be removed from the wishlist, passed as a request parameter.
     * @return A message indicating that the product was removed from the wishlist.
     */
    @Operation(summary = "Remove a product from a wishlist", description = "Remove a product from a wishlist with the given data")
    @ApiResponse(responseCode = "200", description = "Product removed to wishlist")
    @PutMapping("/remove-product")
    public ResponseEntity<String> removeProduct(
            @RequestParam (name = "wishlist") Long wishlistId,
            @RequestParam (name = "product") Long productId) {
        
        wishlistService.removeProductFromWishlist(wishlistId, productId);
        return ResponseEntity.ok(bundle.getString("wishlist.successfully_removed_product"));
    }

    /**
     * This methods rename a wishlist.
     * @param wishlistActualId the ID of the wishlist to be renamed, passed as a request parameter.
     * @param wishlistUpdated the DTO of the wishlist with the new name, passed as a request body.
     * @return A message indicating that the wishlist was renamed.
     */
    @Operation(summary = "Rename a wishlist", description = "Rename a wishlist with the given data")
    @ApiResponse(responseCode = "200", description = "Wishlist renamed")
    @PutMapping("/update")
    public ResponseEntity<String> update(
            @RequestParam(name = "wishlist") Long wishlistActualId,
            @RequestBody @Valid WishlistDTO wishlistUpdated) {

        wishlistService.renameWishlist(wishlistActualId, wishlistUpdated);
        return ResponseEntity.ok(bundle.getString("wishlist.successfully_updated"));
    }

    /**
     * This methods delete a wishlist.
     * @param wishlistId the ID of the wishlist to be deleted, passed as a request parameter.
     * @return A message indicating that the wishlist was deleted.
     */
    @Operation(summary = "Delete a wishlist", description = "Delete a wishlist by its ID")
    @ApiResponse(responseCode = "200", description = "Wishlist deleted")
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam (name = "wishlist") Long wishlistId) {

        wishlistService.deleteWishlist(wishlistId);
        return ResponseEntity.ok(bundle.getString("wishlist.successfully_deleted"));
    }
}
