package com.joshua.qrmenu.repositories;

import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Long> {

    // Only used in testing TODO: remove this
    @Query("SELECT sc FROM SubcategoryEntity sc WHERE sc.categoryEntity.categoryId = :categoryId")
    List<SubcategoryEntity> findAllOfCategory(@Param("categoryId") Long categoryId);

    // Retrieves all subcategoryEntities of a specific category, sorted in a specific order.
    @Query("SELECT sc FROM SubcategoryEntity sc WHERE sc.categoryEntity.categoryId = :categoryId")
    List<SubcategoryEntity> findAllOfCategory(@Param("categoryId") Long categoryId, Sort sort);

    // Retrieves a subcategory of a category by specific IDs.
    @Query("SELECT sc FROM SubcategoryEntity sc WHERE sc.subcategoryId = :subcategoryId AND sc.categoryEntity.categoryId = :categoryId")
    Optional<SubcategoryEntity> findById(@Param("categoryId") Long categoryId, @Param("subcategoryId") Long subcategoryId);

    // Retrieves a subcategory by name of a specific category.
    @Query("SELECT sc FROM SubcategoryEntity sc WHERE sc.name = :name AND sc.categoryEntity.categoryId = :categoryId")
    Optional<SubcategoryEntity> findByName(@Param("categoryId") Long categoryId, @Param("name") String name);

    // Deletes a subcategory by id of a specific category.
    @Query("DELETE FROM SubcategoryEntity sc WHERE sc.subcategoryId = :subcategoryId AND sc.categoryEntity.categoryId = :categoryId")
    @Modifying
    @Transactional
    void deleteById(@Param("categoryId") Long categoryId, @Param("subcategoryId") Long subcategoryId);
}
