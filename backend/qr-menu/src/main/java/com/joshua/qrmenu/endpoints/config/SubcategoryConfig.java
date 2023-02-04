package com.joshua.qrmenu.endpoints.config;

import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.services.SubcategoryService;

import java.util.List;

public class SubcategoryConfig {

//    public static void populateSubcategories(CategoryService categoryService, SubcategoryService subcategoryService, ProductService productService) throws NotFoundException, InputException, AlreadyExistsException {
//        List<Category> categoryList = categoryService.getAll();
//        List<Product> productList = productService.getAll();
//        List<Long> prodIds1 = productList.subList(0, 5).stream().map(Product::getProductId).toList();
//        List<Long> prodIds2 = productList.subList(5, 9).stream().map(Product::getProductId).toList();
//        List<Long> prodIds3 = productList.subList(9, 11).stream().map(Product::getProductId).toList();
//        List<Long> prodIds4 = productList.subList(11, 13).stream().map(Product::getProductId).toList();

//        subcategoryService.createNewSubcategory(categoryList.get(0).getCategoryId(), new NewSubcategory("Non-alcoholisch", prodIds1));
//        subcategoryService.createNewSubcategory(categoryList.get(0).getCategoryId(), new NewSubcategory("Alcoholisch", prodIds2));
//        subcategoryService.createNewSubcategory(categoryList.get(0).getCategoryId(), new NewSubcategory("Subcat3", prodIds3));
//        subcategoryService.createNewSubcategory(categoryList.get(0).getCategoryId(), new NewSubcategory("Subcat4", prodIds4));
//
//        subcategoryService.createNewSubcategory(categoryList.get(1).getCategoryId(), new NewSubcategory("Met frieten", prodIds2));
//        subcategoryService.createNewSubcategory(categoryList.get(2).getCategoryId(), new NewSubcategory("Kroketten", prodIds3));
//
//        int i = 0;
//        // Bieren
//        while ( i <= 4) {
//            subcategoryService.createNewSubcategory(categoryList.get(0).getCategoryId(), new NewSubcategory(subcategoryNames[i], prodIds1, i));
//            i += 1;
//        }
//        subcategoryService.createNewSubcategory(categoryList.get(1).getCategoryId(), new NewSubcategory(subcategoryNames[i], prodIds2, i));
//        i += 1;
//        while (i <= 11) {
//            subcategoryService.createNewSubcategory(categoryList.get(2).getCategoryId(), new NewSubcategory(subcategoryNames[i], prodIds1, i));
//            i += 1;
//        }
//        while( i <= 15) {
//            subcategoryService.createNewSubcategory(categoryList.get(3).getCategoryId(), new NewSubcategory(subcategoryNames[i], prodIds1, i));
//            i += 1;
//        }
//        subcategoryService.createNewSubcategory(categoryList.get(4).getCategoryId(), new NewSubcategory(subcategoryNames[i], prodIds1, i));
//        i += 1;
//        while (i <= 18) {
//            subcategoryService.createNewSubcategory(categoryList.get(5).getCategoryId(), new NewSubcategory(subcategoryNames[i], prodIds1, i));
//            i += 1;
//        }
//        while (i <= 21) {
//            subcategoryService.createNewSubcategory(categoryList.get(6).getCategoryId(), new NewSubcategory(subcategoryNames[i], prodIds1, i));
//            i += 1;
//        }
//        while (i <= 26) {
//            subcategoryService.createNewSubcategory(categoryList.get(7).getCategoryId(), new NewSubcategory(subcategoryNames[i], prodIds1, i));
//            i += 1;
//        }
//        while (i <= 31) {
//            subcategoryService.createNewSubcategory(categoryList.get(2).getCategoryId(), new NewSubcategory(subcategoryNames[i], prodIds1, i));
//            i += 1;
//        }
//        while (i <= 34) {
//            subcategoryService.createNewSubcategory(categoryList.get(2).getCategoryId(), new NewSubcategory(subcategoryNames[i], prodIds1, i));
//            i += 1;
//        }

//    }
//
//    private static final String[] subcategoryNames = new String[] {
//            "Van't vat",
//            "Trappisten",
//            "Belgische bieren",
//            "Buitenlandse bieren",
//            "Alcoholvrije bieren",
//            "Frisdranken",
//            "Versgeperst",
//            "Minute Maid",
//            "Fruit Smoothies",
//            "Groenten Smoothies",
//            "Milkshakes",
//            "Ice Coffee",
//            "Warme dranken",
//            "Koffies",
//            "Kannetje thee",
//            "Speciale koffies",
//            "Theespecialiteiten",
//            "Aperitieven",
//            "Alcoholvrije aperitieven",
//            "Borrelhapjes koud",
//            "Borrelhapjes warm",
//            "Nacho's",
//            "Wijnkaart",
//            "Rode wijnen",
//            "Witte wijnen",
//            "Rosé wijnen",
//            "Schuimwijnen",
//            "Alcohol en Likeuren",
//            "Classic Malt Whisky's",
//            "Eau de Vie",
//            "Gin (tonic niet inbegrepen)",
//            "Tonics",
//            "Tearoom",
//            "Warme appeltaart",
//            "Roomijs",
//            "Soepen",
//            "Snacks (slaatje inbegrepen)",
//            "Croques (2 stuks)",
//            "Salades",
//            "Pasta's met vlees",
//            "Visgerechten met pasta",
//            "Pasta's met kip",
//            "Vegetarische pasta's",
//            "Supplementen",
//            "Vleesgerechten met frietjes",
//            "Visgerechten met frietjes",
//            "Supplementen",
//            "Pasta",
//            "Croques (1 stuk)",
//            "Snacks",
//            "Frietgerechten"
//    };
}
