package com.joshua.qrmenu.endpoints.config;

import com.joshua.qrmenu.models.entities.MetadataEntity;
import com.joshua.qrmenu.repositories.MetaDataRepository;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.services.SubcategoryService;
import com.joshua.qrmenu.util.ExcelReader;

import java.io.File;

/**
 * Class with method to populate the database with data from an .xls file.
 */
public class PopulateConfig {

    // Location of the data file.
    private static final String FILE_LOCATION = "src/main/java/com/joshua/qrmenu/util/data.xls";

    /**
     * Populates the database only once.
     * @param productService : A ProductService.
     * @param categoryService : A CategoryService.
     * @param subcategoryService : A SubcategoryService.
     * @param metaDataRepository : A MetadataRepository to add a key which guarantees this only happens once.
     * @throws Exception : If anything goes wrong.
     */
    public static void populateDatabaseBean(ProductService productService,
                                            CategoryService categoryService,
                                            SubcategoryService subcategoryService,
                                            MetaDataRepository metaDataRepository
    ) throws Exception {
        if (!metaDataRepository.existsByKey("populate")) {
            ExcelReader excelReader = new ExcelReader(productService, categoryService, subcategoryService);
            excelReader.readExcel(FILE_LOCATION);
            metaDataRepository.save(new MetadataEntity("populate", "true"));
        }
    }
}
