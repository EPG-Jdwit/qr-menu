package com.joshua.qrmenu.endpoints.config;

import com.joshua.qrmenu.models.entities.MetadataEntity;
import com.joshua.qrmenu.repositories.MetaDataRepository;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.services.SubcategoryService;

public class PopulateConfig {

    public static void populateDatabaseBean(ProductService productService,
                                            CategoryService categoryService,
                                            SubcategoryService subcategoryService,
                                            MetaDataRepository metaDataRepository
    ) throws Exception {
        if (!metaDataRepository.existsByKey("populate")) {
            ProductConfig.populateProducts(productService);
            CategoryConfig.populateCategories(categoryService);
            SubcategoryConfig.populateSubcategories(categoryService, subcategoryService, productService);
            metaDataRepository.save(new MetadataEntity("populate", "true"));
        }
    }
}
