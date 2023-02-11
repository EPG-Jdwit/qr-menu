package com.joshua.qrmenu.repositories;

import com.joshua.qrmenu.models.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // Retrieves a ProductEntity by name if found.
    @Query("SELECT p FROM ProductEntity p WHERE p.name = :name")
    Optional<ProductEntity> findByName(@Param("name") String name);
}

