package com.joshua.qrmenu.endpoints.config;

import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.models.json.Subcategory;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.services.SubcategoryService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubcategoryConfig {

    public static void populateSubcategories(CategoryService categoryService, SubcategoryService subcategoryService, ProductService productService) throws NotFoundException, InputException, AlreadyExistsException {
        List<Category> categoryList = categoryService.getAll();
        List<Product> productList = productService.getAll();
        Set<Long> prodIds1 = productList.subList(0, 5).stream().map(Product::getProductId).collect(Collectors.toSet());
        Set<Long> prodIds2 = productList.subList(5, 9).stream().map(Product::getProductId).collect(Collectors.toSet());
        Set<Long> prodIds3 = productList.subList(9, 11).stream().map(Product::getProductId).collect(Collectors.toSet());
        Set<Long> prodIds4 = productList.subList(11, 13).stream().map(Product::getProductId).collect(Collectors.toSet());

        subcategoryService.createNewSubcategory(categoryList.get(0).getCategoryId(), new NewSubcategory("Non-alcoholisch", prodIds1));
        subcategoryService.createNewSubcategory(categoryList.get(0).getCategoryId(), new NewSubcategory("Alcoholisch", prodIds2));
        subcategoryService.createNewSubcategory(categoryList.get(0).getCategoryId(), new NewSubcategory("Subcat3", prodIds3));
        subcategoryService.createNewSubcategory(categoryList.get(0).getCategoryId(), new NewSubcategory("Subcat4", prodIds4));

        subcategoryService.createNewSubcategory(categoryList.get(1).getCategoryId(), new NewSubcategory("Met frieten", prodIds2));
        subcategoryService.createNewSubcategory(categoryList.get(2).getCategoryId(), new NewSubcategory("Kroketten", prodIds3));
    }
}
