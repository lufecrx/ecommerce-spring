package br.com.lufecrx.demo.ecommerce.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.lufecrx.demo.ecommerce.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE " +
           "(:productName IS NULL OR p.productName LIKE %:productName%) AND " +
           "(:categoryName IS NULL OR c.name LIKE %:categoryName%) AND " +
           "(p.price BETWEEN :minPrice AND :maxPrice)")
    Optional<Page<Product>> findByNameAndCategoryAndPriceRange(@Param("productName") String productName, 
                                                               @Param("categoryName") String categoryName, 
                                                               @Param("minPrice") Double minPrice, 
                                                               @Param("maxPrice") Double maxPrice, 
                                                               Pageable pageable);
}   
