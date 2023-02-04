package com.joshua.qrmenu.endpoints.config;

import com.joshua.qrmenu.models.entities.MetadataEntity;
import com.joshua.qrmenu.repositories.MetaDataRepository;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.services.SubcategoryService;
import com.joshua.qrmenu.util.ExcelReader;

import java.io.File;

public class PopulateConfig {

    private static final String FILE_LOCATION = "src/main/java/com/joshua/qrmenu/util/data.xls";
    public static void populateDatabaseBean(ProductService productService,
                                            CategoryService categoryService,
                                            SubcategoryService subcategoryService,
                                            MetaDataRepository metaDataRepository
    ) throws Exception {
        if (!metaDataRepository.existsByKey("populate")) {
//            ProductConfig.populateProducts(productService);
//            CategoryConfig.populateCategories(categoryService);
//            SubcategoryConfig.populateSubcategories(categoryService, subcategoryService, productService);
            ExcelReader excelReader = new ExcelReader(productService, categoryService, subcategoryService);
            excelReader.readExcel(FILE_LOCATION);
            metaDataRepository.save(new MetadataEntity("populate", "true"));
        }
    }
}
