package br.com.lufecrx.demo.ecommerce.api.model.dto;

import java.util.HashSet;
import java.util.Set;

import br.com.lufecrx.demo.ecommerce.api.model.Product;

/**
 * Class to represent the product DTO.
 * @param name the name of the product.
 * @param price the price of the product.
 * @param categories the categories of the product.
 * 
 * @see Product
 */
public record ProductDTO(
        String name,
        Double price,
        Set<CategoryDTO> categories) {

    /**
     * Method to convert a product to a product DTO.
     * @param product the product to be converted.
     * @return the product DTO.
     */
    public static ProductDTO from(Product product) {
        Set<CategoryDTO> categories = product.getCategories() != null ? CategoryDTO.from(product.getCategories()) : new HashSet<>();
        return new ProductDTO(product.getProductName(), product.getPrice(), categories);
    }

    /**
     * Method to convert a set of products to a set of product DTOs.
     * @param products the set of products to be converted.
     * @return the set of product DTOs.
     */
    public static Set<ProductDTO> from(Set<Product> products) {
        return products.stream().map(ProductDTO::from).collect(java.util.stream.Collectors.toSet());
    }
}