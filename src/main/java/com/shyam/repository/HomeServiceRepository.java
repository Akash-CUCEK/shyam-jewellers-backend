package com.shyam.repository;

import com.shyam.entity.ServiceHome;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HomeServiceRepository extends JpaRepository<ServiceHome,Long> {
    List<ServiceHome> findAllByOrderByCreatedAtDesc();

    @Query("SELECT s FROM ServiceHome s " +
            "WHERE (:serviceId IS NULL OR s.serviceId = :serviceId) " +
            "OR (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<ServiceHome> searchHomeServices(@Param("serviceId") Long serviceId,
                                         @Param("name") String name);
}
