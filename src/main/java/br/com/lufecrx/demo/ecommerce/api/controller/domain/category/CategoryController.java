package br.com.lufecrx.demo.ecommerce.api.controller.domain.category;

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

import br.com.lufecrx.demo.ecommerce.api.model.Category;
import br.com.lufecrx.demo.ecommerce.api.model.dto.CategoryDTO;
import br.com.lufecrx.demo.ecommerce.api.service.domain.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 * The CategoryController class is responsible for handling the HTTP requests related to categories.
 * It uses the CategoryService to perform operations on the database. 
 * 
 * @see CategoryService
 * @see Category
 */
@RestController
@ApiResponse(responseCode = "403", description = "You are not authorized to access this resource")
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(@Qualifier("standard") CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * This method returns a specific category by its ID. 
     * @param categoryId The ID of the category to be found, passed as a request parameter.
     * @return The DTO of the category found.
     */
    @Operation(summary = "Find a category by its ID", description = "Find a category by its ID and return its DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category found"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/find")
    public ResponseEntity<CategoryDTO> findById(
            @RequestParam(name = "category") Long categoryId) {
        
        Optional<CategoryDTO> opt = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(opt.get());
    }

    /**
     * This method creates a new category with the given data.
     * @param categoryDTO The DTO of the category to be created, passed as a request body.
     * @return A message indicating that the category was created.
     */
    @Operation(summary = "Create a new category", description = "Create a new category with the given data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category created"),
        @ApiResponse(responseCode = "409", description = "Category already exists")
    })
    @PostMapping("/add")
    public ResponseEntity<String> save(
            @RequestBody @Valid CategoryDTO categoryDTO) {
        
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(bundle.getString("category.successfully_created"));   
    }

    /**
     * This method renames a category with the given data.
     * @param categoryActualId The ID of the category to be renamed, passed as a request parameter.
     * @param categoryDTO The DTO of the category with the new name, passed as a request body.
     * @return A message indicating that the category was renamed.
     */
    @Operation(summary = "Rename a category", description = "Rename a category with the given data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category updated"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/rename")
    public ResponseEntity<String> rename(
            @RequestParam(name = "category") Long categoryIdToBeRenamed, 
            @RequestBody @Valid CategoryDTO categoryWithNewName) {
        
        categoryService.renameCategory(categoryIdToBeRenamed, categoryWithNewName);
        return ResponseEntity.ok(bundle.getString("category.successfully_updated"));
    }

    /**
     * This method deletes a category by its ID.
     * @param categoryId The ID of the category to be deleted, passed as a request parameter.
     * @return A message indicating that the category was deleted.
     */
    @Operation(summary = "Delete a category", description = "Delete a category by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category deleted"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam(name = "category") Long categoryId) {
        
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(bundle.getString("category.successfully_deleted"));
    }

}
