package com.joshua.qrmenu.endpoints.config;

import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.services.ProductService;

public class ProductConfig {

    public static void populateProducts(ProductService productService) throws Exception {

        productService.createNewProduct(new NewProduct("Stella 25", 2.9, "Kleine Stella van't vat"));
        productService.createNewProduct(new NewProduct("Fanta", 3.0, "Fanta Orange"));
        productService.createNewProduct(new NewProduct("Vol-au-vent", 19.5, "Vol-au-vent met sla en frietjes"));
        productService.createNewProduct(new NewProduct("Dagsoep", 6.5, "Soep van de dag"));

    }
}
