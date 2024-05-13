package br.com.lufecrx.demo.ecommerce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufecrx.demo.ecommerce.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}   
