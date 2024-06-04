package br.com.lufecrx.demo.ecommerce.api.model.dto;

import java.util.HashSet;
import java.util.Set;

import br.com.lufecrx.demo.ecommerce.api.model.Wishlist;

/**
 * Class to represent the wishlist DTO.
 * @param name the name of the wishlist.
 * @param products the products of the wishlist.
 * 
 * @see Wishlist
 */
public record WishlistDTO(
    String name,
    Set<ProductDTO> products
) {

    /**
     * Method to convert a wishlist to a wishlist DTO.
     * @param wishlist the wishlist to be converted.
     * @return the wishlist DTO.
     */
    public static WishlistDTO from(Wishlist wishlist) {
        Set<ProductDTO> productsDTO = wishlist.getProducts() != null ? ProductDTO.from(wishlist.getProducts()) : new HashSet<>();
        return new WishlistDTO(wishlist.getName(), productsDTO);
    }
} 

