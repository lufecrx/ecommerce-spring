package br.com.lufecrx.demo.ecommerce.api.controller.domain.category;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufecrx.demo.ecommerce.api.model.Category;
import br.com.lufecrx.demo.ecommerce.api.service.domain.category.CategoryServicePaginable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The CategoryControllerPaginable class is responsible for handling the HTTP requests related to categories with pagination.
 * It uses the CategoryServicePaginable to perform operations on the database. 
 * 
 * @see CategoryServicePaginable
 * @see Category
 */
@RestController
@RequestMapping("/categories/paginable")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Categories found"),
    @ApiResponse(responseCode = "400", description = "Invalid arguments to pagination"),
    @ApiResponse(responseCode = "404", description = "Categories not found")
})
public class CategoryControllerPaginable {

    private CategoryServicePaginable categoryService;

    public CategoryControllerPaginable(@Qualifier("paginable") CategoryServicePaginable categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * This method returns all categories with pagination, sorting the results by the given fields.
     * @param page The page number to be returned.
     * @param size The number of elements per page. Note: the default value is 10 and the maximum value is 60.
     * @param sort An array of strings with the format "field,direction" to sort the results. Note: the default value is "name,asc".
     * @return The list of categories found.
     */
    @Operation(summary = "Find all categories with pagination", 
            description = "Find all categories with pagination. Note: Maximum size is 60.")
    @GetMapping()
    public ResponseEntity<Iterable<Category>> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name,asc") String[] sort) {
        Iterable<Category> entities = categoryService.getWithPagination(page, size, sort);
        return ResponseEntity.ok(entities);
    }
}
