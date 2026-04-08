package com.shopeasy.repository;

import com.shopeasy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Filter by category
    List<Product> findByCategory(String category);

    // Search by name or description (case-insensitive)
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> searchProducts(String query);

    // Get all sorted by price ascending
    List<Product> findAllByOrderByPriceAsc();

    // Get all sorted by price descending
    List<Product> findAllByOrderByPriceDesc();
}
