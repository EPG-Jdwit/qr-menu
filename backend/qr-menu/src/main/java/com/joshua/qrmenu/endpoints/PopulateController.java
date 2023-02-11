package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.config.PopulateConfig;
import com.joshua.qrmenu.repositories.MetaDataRepository;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.services.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint to generate data provided in data.xls
 */
@RestController
public class PopulateController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private MetaDataRepository metaDataRepository;

    /**
     * Populates the database from a data.xls file
     *
     * @throws Exception : If something goes wrong.
     */
    @GetMapping("/populate")
    public void populate() throws Exception {
        PopulateConfig.populateDatabaseBean(productService, categoryService, subcategoryService, metaDataRepository);
    }
}
