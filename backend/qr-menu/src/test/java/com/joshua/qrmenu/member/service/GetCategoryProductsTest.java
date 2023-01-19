package com.joshua.qrmenu.member.service;

import com.joshua.qrmenu.member.MemberEnvironment;
import com.joshua.qrmenu.services.MemberService;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class GetCategoryProductsTest {

    private final MemberEnvironment env = new MemberEnvironment();

    private final MemberService memberService = env.initService();

    private final ProductMocker productMocker = new ProductMocker();
    private final CategoryMocker categoryMocker = new CategoryMocker();

    //TODO: move this to other UNITS
    @Test
    public void newCategoryNoProducts() throws Exception {

    }

    //TODO: move this to other UNITS
    @Test
    public void newProductNoCategories() throws Exception {

    }

    // To Category
    @Test
    public void addProductToCategory() throws Exception {

    }

    @Test
    public void addMultipleProductsToCategory() throws Exception {

    }

    @Test
    public void addProductToNonExistingCategory() throws Exception {

    }

    @Test
    public void addNonExistingProductToCategory() throws Exception {

    }

    @Test
    public void addExistingAndNonExistingProductsToCategory() throws Exception {

    }

    // To Product

    @Test
    public void addCategoryToProduct() throws Exception {

    }

    @Test
    public void addMultipleCategoriesToProduct() throws Exception {

    }

    @Test
    public void addCategoryToNonExistingProduct() throws Exception {

    }

    @Test
    public void addNonExistingCategoryToProduct() throws Exception {

    }

    @Test
    public void addExistingAndNonExistingCategoriesToProduct() throws Exception {

    }
}
