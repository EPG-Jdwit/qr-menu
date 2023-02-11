package com.joshua.qrmenu.models.mappers;

import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Maps Data Transfer Objects to Entities and back.
 */
@Component
public class CategoryMapper implements Mapper<CategoryEntity, Category, NewCategory> {

    private final TypeMap<CategoryEntity, Category> categoryEntityCategoryTypeMap;

    private final TypeMap<NewCategory, CategoryEntity> newCategoryToCategoryEntityTypeMap;

    /**
     * Constructor
     */
    public CategoryMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        categoryEntityCategoryTypeMap = modelMapper
                .typeMap(CategoryEntity.class, Category.class);
        newCategoryToCategoryEntityTypeMap = modelMapper
                .typeMap(NewCategory.class, CategoryEntity.class);
    }

    /**
     * Maps a NewCategory to CategoryEntity.
     * @param newCategory : A NewCategory with data.
     * @return : A CategoryEntity with fields filled with the corresponding data from the input.
     */
    @Override
    public CategoryEntity newJsonToEntity(NewCategory newCategory) {
        return newCategoryToCategoryEntityTypeMap.map(newCategory);
    }

    /**
     * Maps a CategoryEntity to a Category.
     * @param categoryEntity : A CategoryEntity with data.
     * @return : A Category with fields filled with the corresponding data from the entity.
     */
    @Override
    public Category entityToJson(CategoryEntity categoryEntity) {
        return categoryEntityCategoryTypeMap.map(categoryEntity);
    }
}
