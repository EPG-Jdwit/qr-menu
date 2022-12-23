package com.joshua.qrmenu.services;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.entities.CategoryEntity;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.models.mappers.CategoryMapper;
import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.repositories.CategoryRepository;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.services.util.AbstractService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService extends AbstractService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    public MemberService(ProductRepository productRepository, CategoryRepository categoryRepository,
                         ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    public List<Product> getCategoryProducts(Long categoryId) throws NotFoundException {
        CategoryEntity categoryEntity = parseOptional(categoryRepository.findById(categoryId));
        List<Product> results = new ArrayList<>();
        for (ProductEntity productEntity : categoryEntity.getProducts()) {
            results.add(productMapper.entityToJson(productEntity));
        }
        return results;
    }
}
