package com.joshua.qrmenu.models.mappers;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements Mapper<CategoryEntity, Category, NewCategory> {

    private final TypeMap<CategoryEntity, Category> categoryEntityCategoryTypeMap;

    private final TypeMap<NewCategory, CategoryEntity> newCategoryToCategoryEntityTypeMap;

    public CategoryMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        categoryEntityCategoryTypeMap = modelMapper
                .typeMap(CategoryEntity.class, Category.class);
        newCategoryToCategoryEntityTypeMap = modelMapper
                .typeMap(NewCategory.class, CategoryEntity.class);
    }
    @Override
    public CategoryEntity newJsonToEntity(NewCategory newCategory) {
        return newCategoryToCategoryEntityTypeMap.map(newCategory);
    }

    @Override
    public Category entityToJson(CategoryEntity categoryEntity) {
        return categoryEntityCategoryTypeMap.map(categoryEntity);
    }
}
