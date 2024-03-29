package com.joshua.qrmenu.repositories;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    // Retrieves a CategoryEntity by name if found.
    @Query("SELECT c FROM CategoryEntity c WHERE c.name = :name")
    Optional<CategoryEntity> findByName(@Param("name") String name);

}
