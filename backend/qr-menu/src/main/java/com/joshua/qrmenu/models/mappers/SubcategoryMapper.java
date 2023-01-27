package com.joshua.qrmenu.models.mappers;

import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Subcategory;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryMapper implements Mapper<SubcategoryEntity, Subcategory, NewSubcategory> {

    private final TypeMap<SubcategoryEntity, Subcategory> subcategoryEntityToSubcategoryTypeMap;

    private final TypeMap<NewSubcategory, SubcategoryEntity> newSubcategoryToSubcategoryEntityTypeMap;

    public SubcategoryMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        subcategoryEntityToSubcategoryTypeMap = modelMapper
                .typeMap(SubcategoryEntity.class, Subcategory.class);
        newSubcategoryToSubcategoryEntityTypeMap = modelMapper
                .typeMap(NewSubcategory.class, SubcategoryEntity.class);
    }

    @Override
    public SubcategoryEntity newJsonToEntity(NewSubcategory newSubcategory) {
        return newSubcategoryToSubcategoryEntityTypeMap.map(newSubcategory);
    }

    @Override
    public Subcategory entityToJson(SubcategoryEntity subcategoryEntity) {
        return subcategoryEntityToSubcategoryTypeMap.map(subcategoryEntity);
    }
}
