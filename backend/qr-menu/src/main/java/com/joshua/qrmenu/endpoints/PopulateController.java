package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.config.PopulateConfig;
import com.joshua.qrmenu.repositories.MetaDataRepository;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PopulateController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MetaDataRepository metaDataRepository;

    @GetMapping("/populate")
    public void populate() throws Exception {
        PopulateConfig.populateDatabaseBean(productService, categoryService, metaDataRepository);
    }
}
