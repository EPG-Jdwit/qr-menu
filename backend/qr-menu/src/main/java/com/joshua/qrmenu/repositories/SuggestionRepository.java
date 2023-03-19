package com.joshua.qrmenu.repositories;

import com.joshua.qrmenu.models.entities.SuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SuggestionRepository extends JpaRepository<SuggestionEntity, Long> {

    // Retrieves a SuggestionEntity by name if found.
    @Query("SELECT sg FROM SuggestionEntity sg WHERE sg.url = :url")
    Optional<SuggestionEntity> findByUrl(@Param("url") String url);
}
