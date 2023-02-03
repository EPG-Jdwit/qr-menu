package com.joshua.qrmenu.util.mocker.repositories;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.repositories.SubcategoryRepository;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class SubcategoryRepositoryMocker {

    private static long idCounter = 0L;

    private SubcategoryRepositoryMocker() {}

    public static SubcategoryRepository init() {
        SubcategoryRepository subcategoryRepository = Mockito.mock(SubcategoryRepository.class);
        when(subcategoryRepository.existsById(nullable(Long.class))).thenReturn(false);
        when(subcategoryRepository.findById(nullable(Long.class))).thenReturn(Optional.empty());
        when(subcategoryRepository.findByName(nullable(String.class), nullable(Long.class))).thenReturn(Optional.empty());
        when(subcategoryRepository.findAll()).thenReturn(new ArrayList<>());
        when(subcategoryRepository.findAllOfCategory(nullable(Long.class))).thenReturn(new ArrayList<>());

        // Mock save
        when(subcategoryRepository.save(nullable(SubcategoryEntity.class))).then(args -> {
            SubcategoryEntity subcategoryEntity = args.getArgument(0);
            // Mock ID generation new object
            if (subcategoryEntity.getSubcategoryId() == null) {
                subcategoryEntity.setSubcategoryId(idCounter++);
            } else {
                // Delete old value and replace with new
                remove(subcategoryRepository, subcategoryEntity);
            }
            add(subcategoryRepository, subcategoryEntity);
            return subcategoryEntity;
        });

        //Mock deleteByID
        doAnswer(args -> {
            Long id = args.getArgument(0);
            Optional<SubcategoryEntity> optionalSubcategoryEntity = subcategoryRepository.findById(id);
            if (optionalSubcategoryEntity.isPresent()) {
                SubcategoryEntity subcategoryEntity = optionalSubcategoryEntity.get();
                Long categoryId = subcategoryEntity.getCategoryEntity().getCategoryId();
                if (subcategoryEntity.getSubcategoryId() != null) {
                    when(subcategoryRepository.existsById(subcategoryEntity.getSubcategoryId())).thenReturn(false);
                    when(subcategoryRepository.findById(subcategoryEntity.getSubcategoryId())).thenReturn(Optional.empty());
                    when(subcategoryRepository.findByName(nullable(String.class), nullable(Long.class))).thenReturn(Optional.empty());
                    List<SubcategoryEntity> allSubcategoryEntities = subcategoryRepository.findAll();
                    allSubcategoryEntities.remove(subcategoryEntity);
                    when(subcategoryRepository.findAll()).thenReturn(allSubcategoryEntities);
                    allSubcategoryEntities = subcategoryRepository.findAllOfCategory(categoryId);
                    allSubcategoryEntities.remove(subcategoryEntity);
                    when(subcategoryRepository.findAllOfCategory(categoryId)).thenReturn(allSubcategoryEntities);
                }
            } else {
                throw new NotFoundException();
            }
            return null;
        }).when(subcategoryRepository).deleteById(nullable(Long.class));

        return subcategoryRepository;
    }
    public static void add(SubcategoryRepository subcategoryRepository, SubcategoryEntity subcategoryEntity) {
        Long categoryId = subcategoryEntity.getCategoryEntity().getCategoryId();
        when(subcategoryRepository.existsById(subcategoryEntity.getSubcategoryId())).thenReturn(true);
        when(subcategoryRepository.findById(subcategoryEntity.getSubcategoryId())).thenReturn(Optional.of(subcategoryEntity));
        when(subcategoryRepository.findByName(subcategoryEntity.getName(), categoryId)).thenReturn(Optional.of(subcategoryEntity));
        List<SubcategoryEntity> currentFindAll = subcategoryRepository.findAll();
        currentFindAll.add(subcategoryEntity);
        when(subcategoryRepository.findAll()).thenReturn(currentFindAll);
        List<SubcategoryEntity> findAllOfCategory = new ArrayList<>(subcategoryRepository.findAllOfCategory(categoryId));
        findAllOfCategory.add(subcategoryEntity);
        when(subcategoryRepository.findAllOfCategory(categoryId)).thenReturn(findAllOfCategory);
    }

    public static void remove(SubcategoryRepository subcategoryRepository, SubcategoryEntity subcategoryEntity) throws NotFoundException {
        Long categoryId = subcategoryEntity.getCategoryEntity().getCategoryId();
        when(subcategoryRepository.existsById(subcategoryEntity.getSubcategoryId())).thenReturn(false);
        when(subcategoryRepository.findById(subcategoryEntity.getSubcategoryId())).thenReturn(Optional.empty());
        when(subcategoryRepository.findByName(subcategoryEntity.getName(), categoryId)).thenReturn(Optional.empty());
        List<SubcategoryEntity> currentFindAll = subcategoryRepository.findAll();
        // Use removeIf to guarantee removal is done based upon ID
        currentFindAll.removeIf( entity -> entity.getSubcategoryId().equals(subcategoryEntity.getSubcategoryId()));
        List<SubcategoryEntity> findAllOfCategory = new ArrayList<>(subcategoryRepository.findAllOfCategory(categoryId));
        findAllOfCategory.removeIf(entity -> entity.getSubcategoryId().equals(subcategoryEntity.getSubcategoryId()));
        when(subcategoryRepository.findAllOfCategory(categoryId)).thenReturn(findAllOfCategory);
    }
}
