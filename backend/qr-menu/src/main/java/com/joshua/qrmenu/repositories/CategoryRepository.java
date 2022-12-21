package com.joshua.qrmenu.repositories;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
