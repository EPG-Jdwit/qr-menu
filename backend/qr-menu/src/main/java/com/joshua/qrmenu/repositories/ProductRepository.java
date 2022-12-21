package com.joshua.qrmenu.repositories;

import com.joshua.qrmenu.models.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}

