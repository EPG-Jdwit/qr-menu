package com.joshua.qrmenu.services.util;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;

public abstract class AbstractSubcategoryService extends AbstractService {

    protected void verifyCategoryId(Long categoryId, SubcategoryEntity subcategoryEntity) throws NotFoundException {
        if  (!subcategoryEntity.getCategoryEntity().getCategoryId().equals(categoryId)) {
            // TODO: Custom exception
            throw new NotFoundException();
        }
    }
}
