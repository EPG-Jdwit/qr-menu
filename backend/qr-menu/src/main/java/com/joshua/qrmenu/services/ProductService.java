package com.joshua.qrmenu.services;

import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.models.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    /**
     * Constructor for a ProductService
     *
     * @param productRepository : a ProductRepository.
     * @param productMapper : a ProductMapper to create EntityModels with HATEAOS links.
     */
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<Product> getAll() {
        Stream<ProductEntity> productEntityStream = productRepository.findAll().stream();

        return productEntityStream
                .map(productMapper::entityToJson)
                .collect(Collectors.toList());

    }

    public Product createNewProduct(NewProduct newProduct) {
        ProductEntity productEntity = productMapper.newJsonToEntity(newProduct);
        productEntity = productRepository.save(productEntity);
        return productMapper.entityToJson(productEntity);
    }
}
