package br.com.lufecrx.demo.ecommerce.api.model.dto;

import java.util.HashSet;
import java.util.Set;

import br.com.lufecrx.demo.ecommerce.api.model.Product;

public record ProductDTO(
        String name,
        Double price,
        Set<CategoryDTO> categories) {

    public static ProductDTO from(Product product) {
        Set<CategoryDTO> categories = product.getCategories() != null ? CategoryDTO.from(product.getCategories()) : new HashSet<>();
        return new ProductDTO(product.getProductName(), product.getPrice(), categories);
    }

    public static Set<ProductDTO> from(Set<Product> products) {
        return products.stream().map(ProductDTO::from).collect(java.util.stream.Collectors.toSet());
    }
}