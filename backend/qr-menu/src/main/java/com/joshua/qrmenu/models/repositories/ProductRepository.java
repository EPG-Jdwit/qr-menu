package com.joshua.qrmenu.models.repositories;

import com.joshua.qrmenu.models.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

//	List<ProductEntity> findByName(String name);
//
//    ProductEntity findById(long id);
}

