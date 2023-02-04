package com.joshua.qrmenu.repositories;

import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Long> {

    // Only used in testing TODO: remove this
    @Query("SELECT sc FROM SubcategoryEntity sc WHERE sc.categoryEntity.categoryId = :categoryId")
    List<SubcategoryEntity> findAllOfCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT sc FROM SubcategoryEntity sc WHERE sc.categoryEntity.categoryId = :categoryId")
    List<SubcategoryEntity> findAllOfCategory(@Param("categoryId") Long categoryId, Sort sort);

    @Query("SELECT sc FROM SubcategoryEntity sc WHERE sc.categoryEntity.categoryId = :categoryId AND sc.name = :name")
    Optional<SubcategoryEntity> findByName(@Param("name") String name, @Param("categoryId") Long categoryId);
}
