package br.com.lufecrx.demo.ecommerce.api.model.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.lufecrx.demo.ecommerce.api.model.Category;

/**
 * Class to represent the category DTO.
 * Contains the name of the category.
 * @param name the name of the category.
 * 
 * @see Category
 */
public record CategoryDTO(
    String name
) {

    /**
     * Method to convert a category to a category DTO.
     * @param category the category to be converted.
     * @return the category DTO.
     */
    public static CategoryDTO from(Category category)
    {
        return new CategoryDTO(category.getName());
    }

    /**
     * Method to convert a set of categories to a set of category DTOs.
     * @param categories the set of categories to be converted.
     * @return the set of category DTOs.
     */
    public static Set<CategoryDTO> from(Set<Category> categories)
    {
        return categories.stream().map(CategoryDTO::from).collect(Collectors.toSet());
    }    
}
