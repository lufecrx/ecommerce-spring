package br.com.lufecrx.demo.ecommerce.api.model.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.lufecrx.demo.ecommerce.api.model.Category;

public record CategoryDTO(
    String name
) {

    public static CategoryDTO from(Category category)
    {
        return new CategoryDTO(category.getName());
    }

    public static Set<CategoryDTO> from(Set<Category> categories)
    {
        return categories.stream().map(CategoryDTO::from).collect(Collectors.toSet());
    }    
}
