package com.joshua.qrmenu.repositories;

import com.joshua.qrmenu.models.entities.MetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaDataRepository extends JpaRepository<MetadataEntity, Long> {

    boolean existsByKey(String key);
}
