package com.shyam.repository;

import com.shyam.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    Optional<Products> findByName(String name);

    @Query("SELECT p FROM Products p WHERE p.price <= :price ORDER BY p.price DESC")
    List<Products> findProductsUnderPriceDesc(@Param("price") BigDecimal price);

    @Query("SELECT p FROM Products p WHERE p.gender = :gender")
    List<Products> findProductByGender(@Param("gender") String gender);

    Page<Products> findByCategoryAndIsAvailableTrue(
            String category,
            Pageable pageable
    );

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
    Page<Products> findProductsByFilters(
            @Param("category") String category,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minWeight") BigDecimal minWeight,
            @Param("maxWeight") BigDecimal maxWeight,
            @Param("materialType") String materialType,
            @Param("gender") String gender,
            @Param("isAvailable") Boolean isAvailable,
            @Param("minAvailableStock") Integer minAvailableStock,
            @Param("maxAvailableStock") Integer maxAvailableStock,
            Pageable pageable
    );
}
