package com.joshua.qrmenu.util.mocker;


import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.util.mocker.data.ProductDescriptionMocker;
import com.joshua.qrmenu.util.mocker.data.ProductNameMocker;
import com.joshua.qrmenu.util.mocker.data.ProductPriceMocker;

import java.util.ArrayList;
import java.util.List;

public class ProductMocker {

    public ProductMocker() {}

    private static Long createdCounter = 0L;

    private static final ProductNameMocker productNameMocker = new ProductNameMocker();

    private static final ProductPriceMocker productPriceMocker = new ProductPriceMocker();

    private static final ProductDescriptionMocker productDescriptionMocker = new ProductDescriptionMocker();

    public ProductEntity generateProductEntity() {
        createdCounter += 1;
        String productName = productNameMocker.productName();
        Double productPrice = productPriceMocker.productPrice();
        String productDescription = productDescriptionMocker.productDescription();
        return new ProductEntity(createdCounter, productName, productPrice, productDescription, null);
    }

    public List<ProductEntity> generateProductEntities(int number) {
        List<ProductEntity> list = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            list.add(generateProductEntity());
        }
        return list;
    }

    public NewProduct generateNewProduct() {
        createdCounter += 1;
        String productName = productNameMocker.productName();
        Double productPrice = productPriceMocker.productPrice();
        String productDescription = productDescriptionMocker.productDescription();
        return new NewProduct(productName, productPrice, productDescription);
    }

    public NewProduct generateNullNewProduct() {
        createdCounter += 1;
        return new NewProduct(null, null, null);
    }

}
