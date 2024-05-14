package br.com.lufecrx.demo.ecommerce.api.controller.domain.product;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
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
    public ResponseEntity<Iterable<Product>> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name,asc") String[] sort) {
        Iterable<Product> entities = productService.getWithPagination(page, size, sort);
        return ResponseEntity.ok(entities);
    }
}
