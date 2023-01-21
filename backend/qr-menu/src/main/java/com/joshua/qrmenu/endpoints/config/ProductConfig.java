package com.joshua.qrmenu.endpoints.config;

import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.services.ProductService;

public class ProductConfig {

    public static void populateProducts(ProductService productService) throws Exception {

        productService.createNewProduct(new NewProduct("Stella 25", 2000.9, "Kleine Stella van't vat"));
        productService.createNewProduct(new NewProduct("Fanta", 3.0, "Fanta Orange"));
        productService.createNewProduct(new NewProduct("Warme hapjes gemend", 11.5,"Assortiment van loempia's, kippenboutjes, kaasballetjes, garnaalballetjes, bitterballen en calamares ringen"));
        productService.createNewProduct(new NewProduct("Vol-au-vent", 19000.5, "Vol-au-vent met sla en frietjes"));
        productService.createNewProduct(new NewProduct("Dagsoep", 6.5, "Soep van de dag"));
        productService.createNewProduct(new NewProduct("Rodenbach 25", 3.5,"Kleine Rodenbach van't vat"));
        productService.createNewProduct(new NewProduct("Coupe Vanille", 8000.5,""));
        productService.createNewProduct(new NewProduct("Glas witte wijn", 5.0,"Sauvignon blanc"));
        productService.createNewProduct(new NewProduct("Spaghetti Bolognaise", 12.5,""));
        productService.createNewProduct(new NewProduct("Lasagna", 16000.0,""));
    }
}
