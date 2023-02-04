package com.joshua.qrmenu.util.mocker.repositories;


import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.repositories.ProductRepository;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class CategoryRepositoryMocker {

    private static long idCounter = 0L;

    private static final Sort orderSort = Sort.by(Sort.Direction.ASC, "orderNr");

    private CategoryRepositoryMocker() {}

    public static CategoryRepository init() {
        CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
        when(categoryRepository.existsById(nullable(Long.class))).thenReturn(false);
        when(categoryRepository.findById(nullable(Long.class))).thenReturn(Optional.empty());
        when(categoryRepository.findByName(nullable(String.class))).thenReturn(Optional.empty());
        when(categoryRepository.findAll(orderSort)).thenReturn(new ArrayList<>());

        // Mock save
        when(categoryRepository.save(nullable(CategoryEntity.class))).then(args -> {
            CategoryEntity categoryEntity = args.getArgument(0);
            // Mock ID generation new object
            if (categoryEntity.getCategoryId() == null) {
                categoryEntity.setCategoryId(idCounter++);
            } else {
                // Delete old value and replace with new
                remove(categoryRepository, categoryEntity);
            }
            add(categoryRepository, categoryEntity);
            return categoryEntity;
        });

        //Mock deleteByID
        doAnswer(args -> {
            Long id = args.getArgument(0);
            Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(id);
            if (optionalCategoryEntity.isPresent()) {
                CategoryEntity categoryEntity = optionalCategoryEntity.get();
                if (categoryEntity.getCategoryId() != null) {
                    when(categoryRepository.existsById(categoryEntity.getCategoryId())).thenReturn(false);
                    when(categoryRepository.findById(categoryEntity.getCategoryId())).thenReturn(Optional.empty());
                    when(categoryRepository.findByName(nullable(String.class))).thenReturn(Optional.empty());
                    List<CategoryEntity> allCategoryEntities = categoryRepository.findAll(orderSort);
                    allCategoryEntities.remove(categoryEntity);
                    when(categoryRepository.findAll(orderSort)).thenReturn(allCategoryEntities);
                }
            } else {
                throw new NotFoundException();
            }
            return null;
        }).when(categoryRepository).deleteById(nullable(Long.class));

        return categoryRepository;
    }
    public static void add(CategoryRepository categoryRepository, CategoryEntity categoryEntity) {
        when(categoryRepository.existsById(categoryEntity.getCategoryId())).thenReturn(true);
        when(categoryRepository.findById(categoryEntity.getCategoryId())).thenReturn(Optional.of(categoryEntity));
        when(categoryRepository.findByName(categoryEntity.getName())).thenReturn(Optional.of(categoryEntity));
        List<CategoryEntity> currentFindAll = categoryRepository.findAll(orderSort);
        currentFindAll.add(categoryEntity);
        when(categoryRepository.findAll(orderSort)).thenReturn(currentFindAll);
    }

    public static void remove(CategoryRepository categoryRepository, CategoryEntity categoryEntity) throws NotFoundException {
//        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryEntity.getCategoryId());
//        if (optionalCategoryEntity.isEmpty()) {
//            throw new NotFoundException();
//        }
        when(categoryRepository.existsById(categoryEntity.getCategoryId())).thenReturn(false);
        when(categoryRepository.findById(categoryEntity.getCategoryId())).thenReturn(Optional.empty());
        when(categoryRepository.findByName(categoryEntity.getName())).thenReturn(Optional.empty());
        List <CategoryEntity> currentFindAll = categoryRepository.findAll(orderSort);
        currentFindAll.removeIf( entity -> entity.getCategoryId().equals(categoryEntity.getCategoryId()));
    }

}
