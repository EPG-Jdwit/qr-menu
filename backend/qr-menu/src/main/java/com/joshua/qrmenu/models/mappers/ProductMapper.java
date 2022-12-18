package com.joshua.qrmenu.models.mappers;

import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

/**
 * A class that maps ProductEntity objects to Product objects and back.
 */
@Component
public class ProductMapper implements Mapper<ProductEntity, Product, NewProduct>{

    private final TypeMap<ProductEntity, Product> productEntityToProductTypeMap;

    private final TypeMap<NewProduct, ProductEntity> newProductToProductEntityTypeMap;

    public ProductMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        productEntityToProductTypeMap = modelMapper
                .typeMap(ProductEntity.class, Product.class);
        newProductToProductEntityTypeMap = modelMapper
                .typeMap(NewProduct.class, ProductEntity.class);
    }

    /**
     * Maps a NewProduct object to a ProductEntity object.
     *
     * @param newProduct: The object that should be converted.
     * @return : A ProductEntity object equivalent to the given NewProduct
     */
    @Override
    public ProductEntity newJsonToEntity(NewProduct newProduct) {
        return newProductToProductEntityTypeMap.map(newProduct);
    }

    /**
     * Maps a ProductEntity object to a Product object.
     *
     * @param productEntity : The object that should be converted.
     * @return : A Product object equivalent to the given ProductEntity.
     */
    @Override
    public Product entityToJson(ProductEntity productEntity) {
        return productEntityToProductTypeMap.map(productEntity);
    }


}
