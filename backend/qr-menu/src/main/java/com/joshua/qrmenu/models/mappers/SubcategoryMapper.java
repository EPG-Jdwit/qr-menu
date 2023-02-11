package com.joshua.qrmenu.models.mappers;

import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Subcategory;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

/**
 * Maps Data Transfer Objects to Entities and back.
 */
@Component
public class SubcategoryMapper implements Mapper<SubcategoryEntity, Subcategory, NewSubcategory> {

    private final TypeMap<SubcategoryEntity, Subcategory> subcategoryEntityToSubcategoryTypeMap;

    private final TypeMap<NewSubcategory, SubcategoryEntity> newSubcategoryToSubcategoryEntityTypeMap;

    /**
     * Constructor
     */
    public SubcategoryMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        subcategoryEntityToSubcategoryTypeMap = modelMapper
                .typeMap(SubcategoryEntity.class, Subcategory.class)
                .addMappings(mapper ->
                        mapper.map(subcategoryEntity -> subcategoryEntity.getCategoryEntity().getCategoryId(), Subcategory::setCategoryId)
                );
        newSubcategoryToSubcategoryEntityTypeMap = modelMapper
                .typeMap(NewSubcategory.class, SubcategoryEntity.class);
    }

    /**
     * Maps a NewSubcategory to a SubcategoryEntity.
     * @param newSubcategory : A NewSubcategory with data.
     * @return : A SubcategoryEntity with fields filled with the corresponding data from the input.
     */
    @Override
    public SubcategoryEntity newJsonToEntity(NewSubcategory newSubcategory) {
        return newSubcategoryToSubcategoryEntityTypeMap.map(newSubcategory);
    }

    /**
     * Maps a SubcategoryEntity to a Subcategory.
     * @param subcategoryEntity : A SubcategoryEntity with data.
     * @return : A Subcategory with fields filled with the corresponding data from the entity.
     */
    @Override
    public Subcategory entityToJson(SubcategoryEntity subcategoryEntity) {
        return subcategoryEntityToSubcategoryTypeMap.map(subcategoryEntity);
    }
}
