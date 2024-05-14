package br.com.lufecrx.demo.ecommerce.api.controller.domain.product;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufecrx.demo.ecommerce.api.model.Product;
import br.com.lufecrx.demo.ecommerce.api.model.dto.ProductDTO;
import br.com.lufecrx.demo.ecommerce.api.service.domain.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 * The ProductController class is responsible for handling the HTTP requests related to products.
 * It uses the ProductService to perform operations on the database. 
 * 
 * @see ProductService
 * @see Product
 */
@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "You are not authorized to access this resource"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
})
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(@Qualifier("standard") ProductService productService) {
        this.productService = productService;
    }

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * This method creates a new product with the given data.
     * @param productDTO The DTO of the product to be created, passed as a request body.
     * @return A message indicating that the product was successfully created.
     */
    @Operation(summary = "Create a new product", description = "Create a new product with the given data")
    @ApiResponse(responseCode = "200", description = "Product created")
    @PostMapping("/add")
    public ResponseEntity<String> save(
            @RequestBody @Valid ProductDTO productDTO) {
        
        productService.createProduct(productDTO);
        return ResponseEntity.ok(bundle.getString("product.successfully_created"));
    }

    /**
     * This method returns a specific product by its ID. 
     * @param productId The ID of the product to be found, passed as a request parameter.
     * @return The DTO of the product found.
     */
    @Operation(summary = "Find a product by its ID", description = "Find a product by its ID")
    @ApiResponse(responseCode = "200", description = "Product found")
    @GetMapping("/find")
    public ResponseEntity<Optional<ProductDTO>> findById(
            @RequestParam(name = "product") Long productId) {
        
        Optional<ProductDTO> product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    /**
     * This method updates a product with the given data.
     * @param productId The ID of the product to be updated, passed as a request parameter.
     * @param productUpdated The DTO of the product with the new data, passed as a request body.
     * @return A message indicating that the product was successfully updated.
     */
    @Operation(summary = "Update a product", description = "Update a product with the given data")
    @ApiResponse(responseCode = "200", description = "Product updated")
    @PutMapping("/update")
    public ResponseEntity<String> update(
            @RequestParam(name = "product") Long productId,
            @RequestBody @Valid ProductDTO productUpdated) {
        
        productService.updateProduct(productId, productUpdated);
        return ResponseEntity.ok(bundle.getString("product.successfully_updated"));
    }

    /**
     * This method deletes a product by its ID.
     * @param productId The ID of the product to be deleted, passed as a request parameter.
     * @return A message indicating that the product was successfully deleted.
     */
    @Operation(summary = "Delete a product", description = "Delete a product by its ID")
    @ApiResponse(responseCode = "200", description = "Product deleted")
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam(name = "product") @PathVariable Long productId) {

        productService.deleteProduct(productId);
        return ResponseEntity.ok(bundle.getString("product.successfully_deleted"));
    }
}
