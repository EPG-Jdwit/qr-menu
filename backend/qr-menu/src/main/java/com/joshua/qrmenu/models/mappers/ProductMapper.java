package com.joshua.qrmenu.models.mappers;

import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

/**
 * Maps Data Transfer Objects to Entities and back.
 */
@Component
public class ProductMapper implements Mapper<ProductEntity, Product, NewProduct>{

    private final TypeMap<ProductEntity, Product> productEntityToProductTypeMap;

    private final TypeMap<NewProduct, ProductEntity> newProductToProductEntityTypeMap;

    /**
     * Constructor
     */
    public ProductMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        productEntityToProductTypeMap = modelMapper
                .typeMap(ProductEntity.class, Product.class);
        newProductToProductEntityTypeMap = modelMapper
                .typeMap(NewProduct.class, ProductEntity.class);
    }

    /**
     * Maps a NewProduct to ProductEntity.
     *
     * @param newProduct: A NewProduct with data.
     * @return : A ProductEntity with fields filled with the corresponding data from the input
     */
    @Override
    public ProductEntity newJsonToEntity(NewProduct newProduct) {
        return newProductToProductEntityTypeMap.map(newProduct);
    }

    /**
     * Maps a ProductEntity to a Product.
     *
     * @param productEntity : A ProductEntity with data.
     * @return : A Product with fields filled in with the corresponding data from the entity.
     */
    @Override
    public Product entityToJson(ProductEntity productEntity) {
        return productEntityToProductTypeMap.map(productEntity);
    }


}
