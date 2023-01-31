package com.joshua.qrmenu.subproducts.service;

import com.joshua.qrmenu.subproducts.SubProductEnvironment;
import com.joshua.qrmenu.services.SubProductService;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class GetCategoryProductsTest {

    private final SubProductEnvironment env = new SubProductEnvironment();

    private final SubProductService subProductService = env.initService();

    private final ProductMocker productMocker = new ProductMocker();
    private final CategoryMocker categoryMocker = new CategoryMocker();

    //TODO: move this to other UNITS
    @Test
    public void newSubcategoryNoProducts() throws Exception {

    }

    //TODO: move this to other UNITS
    @Test
    public void newProductNoSubcategory() throws Exception {

    }

    // To Category
    @Test
    public void addProductToSubcategory() throws Exception {

    }

    @Test
    public void addMultipleProductsToSubcategory() throws Exception {

    }

    @Test
    public void addProductToNonExistingSubcategory() throws Exception {

    }

    @Test
    public void addNonExistingProductToSubcategory() throws Exception {

    }

    @Test
    public void addExistingAndNonExistingProductsToSubcategory() throws Exception {

    }

    // To Product

    @Test
    public void addSubcategoryToProduct() throws Exception {

    }

    @Test
    public void addMultipleSubcategoryToProduct() throws Exception {

    }

    @Test
    public void addSubcategoryToNonExistingProduct() throws Exception {

    }

    @Test
    public void addNonExistingSubcategoryToProduct() throws Exception {

    }

    @Test
    public void addExistingAndNonExistingSubcategoryToProduct() throws Exception {

    }
}
