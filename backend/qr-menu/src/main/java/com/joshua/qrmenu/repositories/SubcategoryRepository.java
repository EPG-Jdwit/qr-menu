package com.joshua.qrmenu.repositories;

import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Long> {

    @Query("SELECT sc FROM SubcategoryEntity sc WHERE sc.categoryEntity.categoryId = :categoryId")
    List<SubcategoryEntity> findAllOfCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT sc FROM SubcategoryEntity sc WHERE sc.name = :name")
    Optional<SubcategoryEntity> findByName(@Param("name") String name);
}
