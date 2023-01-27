package com.joshua.qrmenu.endpoints.config;

import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.services.ProductService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MemberConfig {

    public static void populateMembers(ProductService productService, CategoryService categoryService) throws Exception {
        List<Product> products = productService.getAll();
        List<Category> categories = categoryService.getAll();

        // Add products to a first category
//        Category category1 = categories.get(0);
//        Category category1 = categories.stream().filter(c -> c.getName().equals("Bier")).toList().get(0);
//        Set<Product> products1 = new HashSet<>(products.subList(0, 6));
//        NewCategory newCategory1 = new NewCategory(category1.getName(), products1.stream().map(Product::getProductId).collect(Collectors.toSet()));
//        Category res = categoryService.patchCategoryById(category1.getCategoryId(), newCategory1);
    }
}
