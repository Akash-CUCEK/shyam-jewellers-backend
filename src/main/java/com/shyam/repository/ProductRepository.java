package com.shyam.repository;


import com.shyam.entity.Products;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    Optional<Products> findByName(String name);

    @Query("SELECT p FROM Products p WHERE p.price <= :price ORDER BY p.price DESC")
    List<Products> findProductsUnderPriceDesc(@Param("price") BigDecimal price);

    @Query("SELECT p FROM Products p WHERE p.category = :category")
    List<Products> findProductByCategory(@Param("category") String category);

    @Query("SELECT p FROM Products p WHERE p.gender = :gender")
    List<Products> findProductByGender(@Param("gender") String gender);

    @Query("SELECT p FROM Products p ORDER BY p.updatedAt DESC NULLS LAST")
    List<Products> getAllProducts();

    @Query("""
    SELECT p FROM Products p
    WHERE (:category IS NULL OR p.category = :category)
      AND (:minPrice IS NULL OR p.price >= :minPrice)
      AND (:maxPrice IS NULL OR p.price <= :maxPrice)
      AND (:minWeight IS NULL OR p.weight >= :minWeight)
      AND (:maxWeight IS NULL OR p.weight <= :maxWeight)
      AND (:materialType IS NULL OR p.materialType = :materialType)
      AND (:gender IS NULL OR p.gender = :gender)
      AND (:isAvailable IS NULL OR p.isAvailable = :isAvailable)
      AND (:minAvailableStock IS NULL OR p.availableStock >= :minAvailableStock)
      AND (:maxAvailableStock IS NULL OR p.availableStock <= :maxAvailableStock)
""")
    List<Products> findProductsByFilters(
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minWeight") Double minWeight,
            @Param("maxWeight") Double maxWeight,
            @Param("materialType") String materialType,
            @Param("gender") String gender,
            @Param("isAvailable") Boolean isAvailable,
            @Param("minAvailableStock") Integer minAvailableStock,
            @Param("maxAvailableStock") Integer maxAvailableStock
    );




}
