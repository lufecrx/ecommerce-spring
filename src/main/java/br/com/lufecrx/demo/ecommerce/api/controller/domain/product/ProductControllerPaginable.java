package br.com.lufecrx.demo.ecommerce.api.controller.domain.product;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.model.dto.ProductDTO;
import br.com.lufecrx.demo.ecommerce.api.service.domain.product.ProductServicePaginable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The ProductControllerPaginable class is responsible for handling the HTTP requests related to products with pagination.
 * It uses the ProductServicePaginable to perform operations on the database. 
 * 
 * @see ProductServicePaginable
 * @see Product
 */
@RestController
@RequestMapping("/products/paginable")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Products found"),
    @ApiResponse(responseCode = "400", description = "Invalid arguments to pagination"),
    @ApiResponse(responseCode = "404", description = "Products not found")
})
public class ProductControllerPaginable {

    private ProductServicePaginable productService;

    public ProductControllerPaginable(@Qualifier("paginable") ProductServicePaginable productService) {
        this.productService = productService;
    }

    /**
     * This method returns all products with pagination, sorting the results by the given fields.
     * @param page The page number to be returned.
     * @param size The number of elements per page. Note: the default value is 10 and the maximum value is 60.
     * @param sort An array of strings with the format "field,direction" to sort the results. Note: the default value is "name,asc".
     * @return The list of products found.
     */
    @Operation(summary = "Find all products with pagination", 
            description = "Find all products with pagination. Note: Maximum size is 60.")
    @GetMapping()
    public ResponseEntity<Iterable<ProductDTO>> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name,asc") String[] sort) {
        Iterable<ProductDTO> entities = productService.getWithPagination(page, size, sort);
        return ResponseEntity.ok(entities);
    }

    /**
     * This method searches for products by name, category, and price range.
     * @param name the name of the product
     * @param categoryName the name of the category
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param page the page number
     * @param size the number of products per page
     * @return A list of products that match the search criteria.
     */
    @Operation(summary = "Search for products", description = "Search for products by name, category, and price range")
    @ApiResponse(responseCode = "200", description = "Products found")
    @GetMapping("/search")
    public ResponseEntity<Iterable<ProductDTO>> search(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "category", required = false) String categoryName,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size, 
            @RequestParam(name = "sort", defaultValue = "id,asc") String[] sort) {
        
        Iterable<ProductDTO> products = productService.searchProducts(name, categoryName, minPrice, maxPrice, page, size, sort);
        return ResponseEntity.ok(products);
    }
}
