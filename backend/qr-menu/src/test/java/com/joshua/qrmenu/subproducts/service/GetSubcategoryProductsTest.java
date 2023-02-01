package com.joshua.qrmenu.subproducts.service;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.services.SubProductService;
import com.joshua.qrmenu.subproducts.SubProductEnvironment;
import com.joshua.qrmenu.util.mocker.CategoryMocker;
import com.joshua.qrmenu.util.mocker.ProductMocker;
import com.joshua.qrmenu.util.mocker.SubcategoryMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetSubcategoryProductsTest {

    private final SubProductEnvironment env = new SubProductEnvironment();

    private final SubProductService subProductService = env.initService();

    private final ProductMocker productMocker = new ProductMocker();

    private final SubcategoryMocker subcategoryMocker = new SubcategoryMocker();

    private final CategoryMocker categoryMocker = new CategoryMocker();

    private final ProductMapper productMapper = new ProductMapper();

    // TODO: avoid code duplication at initialization
    private CategoryEntity categoryEntity;
    private SubcategoryEntity subcategoryEntity;
    private ProductEntity productEntity;

    @Test
    public void notPresentAtStart() {
        assertThrows(NotFoundException.class,
                () -> subProductService.getSubcategoryProducts(0L, 0L)
        );
    }

    @Nested
    @DisplayName("No existing products")
    public class NoProductsTests {
        @BeforeEach
        public void initializeEnv() {
            categoryEntity = categoryMocker.generateCategoryEntity();
            env.addCategoryEntity(categoryEntity);

            subcategoryEntity = subcategoryMocker.generateSubcategoryEntity();
            subcategoryEntity.setCategoryEntity(categoryEntity);
            env.addSubcategoryEntity(subcategoryEntity);
        }

        @Test
        public void NoExistingProducts() throws NotFoundException {
            assertThat(subProductService.getSubcategoryProducts(
                    categoryEntity.getCategoryId(),
                    subcategoryEntity.getSubcategoryId()
            ).size()).isEqualTo(0);
        }

        @Test
        public void noProductsInSubcategory() throws NotFoundException {
            assertThat(subProductService.getSubcategoryProducts(
                    categoryEntity.getCategoryId(),
                    subcategoryEntity.getSubcategoryId()
            ).size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("One existing product in one subcategory")
    public class OneProductTests {

        @BeforeEach
        public void initializeEnv() {
            categoryEntity = categoryMocker.generateCategoryEntity();
            env.addCategoryEntity(categoryEntity);

            productEntity = productMocker.generateProductEntity();
            env.addProductEntity(productEntity);

            subcategoryEntity = subcategoryMocker.generateSubcategoryEntity();
            subcategoryEntity.setCategoryEntity(categoryEntity);
            subcategoryEntity.setProducts(new HashSet<>(Collections.singleton(productEntity)));
            env.addSubcategoryEntity(subcategoryEntity);
        }

        @Test
        public void WrongCategoryId() {
            assertThrows(NotFoundException.class,
                    () -> subProductService.getSubcategoryProducts(
                            categoryEntity.getCategoryId() + 1,
                            subcategoryEntity.getSubcategoryId()
                    )
            );
        }

        @Test
        public void WrongSubcategoryId() {
            assertThrows(NotFoundException.class,
                    () -> subProductService.getSubcategoryProducts(
                            categoryEntity.getCategoryId(),
                            subcategoryEntity.getSubcategoryId() + 1
                    )
            );
        }

        @Test
        public void oneProductInSubcategory() throws NotFoundException {
            List<Product> result = subProductService.getSubcategoryProducts(
                    categoryEntity.getCategoryId(),
                    subcategoryEntity.getSubcategoryId()
            );

            Product product = productMapper.entityToJson(productEntity);

            assertThat(result.size()).isEqualTo(1);
            assertThat(result.contains(product)).isTrue();
        }
    }

    @Nested
    @DisplayName("Multiple products in one subcategory")
    public class MultipleProductTests {

        private Set<ProductEntity> productEntities = new HashSet<>();

        private ProductEntity productEntity2;

        @BeforeEach
        public void initializeEnv() {
            categoryEntity = categoryMocker.generateCategoryEntity();
            env.addCategoryEntity(categoryEntity);

            productEntity = productMocker.generateProductEntity();
            env.addProductEntity(productEntity);
            productEntities.add(productEntity);

            productEntity2 = productMocker.generateProductEntity();
            env.addProductEntity(productEntity2);
            productEntities.add(productEntity2);

            subcategoryEntity = subcategoryMocker.generateSubcategoryEntity();
            subcategoryEntity.setCategoryEntity(categoryEntity);
            subcategoryEntity.setProducts(productEntities);
            env.addSubcategoryEntity(subcategoryEntity);
        }

        @Test
        public void twoProductsInSubcategory() throws NotFoundException {
            List<Product> allProducts = productEntities
                    .stream()
                    .map(productMapper::entityToJson)
                    .toList();

            List<Product> result = subProductService.getSubcategoryProducts(
                    categoryEntity.getCategoryId(),
                    subcategoryEntity.getSubcategoryId()
            );

            assertThat(result.size()).isEqualTo(2);
            assertThat(result.containsAll(allProducts)).isTrue();
        }

        @Nested
        @DisplayName("Multiple subcategories with products")
        public class MultipleSubcategoriesTests {

            private SubcategoryEntity subcategoryEntity2;

            private Set<ProductEntity> productEntities2;

            private ProductEntity productEntity3;
            private ProductEntity productEntity4;
            private ProductEntity productEntity5;

            @BeforeEach
            public void initializeEnv() {
                productEntities2 = new HashSet<>();

                productEntity3 = productMocker.generateProductEntity();
                env.addProductEntity(productEntity3);
                productEntities2.add(productEntity3);

                productEntity4 = productMocker.generateProductEntity();
                env.addProductEntity(productEntity4);
                productEntities2.add(productEntity4);

                productEntity5 = productMocker.generateProductEntity();
                env.addProductEntity(productEntity5);
                productEntities2.add(productEntity5);

                subcategoryEntity2 = subcategoryMocker.generateSubcategoryEntity();
                subcategoryEntity2.setCategoryEntity(categoryEntity);
                subcategoryEntity2.setProducts(productEntities2);
                env.addSubcategoryEntity(subcategoryEntity2);
            }

            @Test
            public void multipleSubcategories() throws NotFoundException {
                List<Product> allProducts1 = productEntities
                        .stream()
                        .map(productMapper::entityToJson)
                        .toList();
                List<Product> allProducts2 = productEntities2
                        .stream()
                        .map(productMapper::entityToJson)
                        .toList();

                List<Product> result = subProductService.getSubcategoryProducts(
                        categoryEntity.getCategoryId(),
                        subcategoryEntity.getSubcategoryId()
                );

                assertThat(result.size()).isEqualTo(2);
                assertThat(result.containsAll(allProducts1)).isTrue();

                result = subProductService.getSubcategoryProducts(
                        categoryEntity.getCategoryId(),
                        subcategoryEntity2.getSubcategoryId()
                );

                assertThat(result.size()).isEqualTo(3);
                assertThat(result.containsAll(allProducts2)).isTrue();
            }
        }

    }
}
