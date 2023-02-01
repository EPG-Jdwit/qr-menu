package com.joshua.qrmenu.services;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.entities.SubcategoryEntity;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.models.mappers.SubcategoryMapper;
import com.joshua.qrmenu.repositories.ProductRepository;
import com.joshua.qrmenu.repositories.SubcategoryRepository;
import com.joshua.qrmenu.services.util.AbstractService;
import com.joshua.qrmenu.services.util.AbstractSubcategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubProductService extends AbstractSubcategoryService {

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;

    private final ProductMapper productMapper;
    private final SubcategoryMapper subcategoryMapper;

    public SubProductService(ProductRepository productRepository, SubcategoryRepository subcategoryRepository,
                             ProductMapper productMapper, SubcategoryMapper subcategoryMapper) {
        this.productRepository = productRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.productMapper = productMapper;
        this.subcategoryMapper = subcategoryMapper;
    }

    public List<Product> getSubcategoryProducts(Long categoryId, Long subcategoryId) throws NotFoundException {
        SubcategoryEntity subcategoryEntity = parseOptional(subcategoryRepository.findById(subcategoryId));
        verifyCategoryId(categoryId, subcategoryEntity);
        List<Product> results = new ArrayList<>();
        for (ProductEntity productEntity : subcategoryEntity.getProducts()) {
            results.add(productMapper.entityToJson(productEntity));
        }
        return results;
    }
}
