package br.com.lufecrx.demo.ecommerce.api.model.dto;

import java.util.HashSet;
import java.util.Set;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;

public record WishlistDTO(
    String name,
    Set<ProductDTO> products
) {
    public static WishlistDTO from(Wishlist wishlist) {
        Set<ProductDTO> productsDTO = wishlist.getProducts() != null ? ProductDTO.from(wishlist.getProducts()) : new HashSet<>();
        return new WishlistDTO(wishlist.getName(), productsDTO);
    }
} 

