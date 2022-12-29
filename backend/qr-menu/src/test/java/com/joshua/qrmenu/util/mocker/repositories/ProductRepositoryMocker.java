package com.joshua.qrmenu.util.mocker.repositories;

import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.repositories.ProductRepository;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

public class ProductRepositoryMocker {

    private static long idCounter = 0L;

    private ProductRepositoryMocker() {}

    public static ProductRepository init() {
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        when(productRepository.existsById(nullable(Long.class))).thenReturn(false);
        when(productRepository.findById(nullable(Long.class))).thenReturn(Optional.empty());
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        //Mock save
        when(productRepository.save(nullable(ProductEntity.class))).then(args -> {
           ProductEntity productEntity = args.getArgument(0);
           // Mock ID generation new object
            if (productEntity.getProductId() == null) {
                productEntity.setProductId(idCounter++);
            } else {
                // Delete old value and replace with new
                remove(productRepository, productEntity);
            }
            add(productRepository, productEntity);
            return productEntity;
        });

        //Mock deleteByID
        doAnswer(args -> {
            Long id = args.getArgument(0);
            Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);
            System.out.println("PRESENT");
            System.out.println(id);
            System.out.println(optionalProductEntity.isPresent());
            if (optionalProductEntity.isPresent()) {
                ProductEntity productEntity = optionalProductEntity.get();
                if (productEntity.getProductId() != null) {
                    when(productRepository.existsById(productEntity.getProductId())).thenReturn(false);
                    when(productRepository.findById(productEntity.getProductId())).thenReturn(Optional.empty());
                    List<ProductEntity> allProductEntities = productRepository.findAll();
                    allProductEntities.remove(productEntity);
                    when(productRepository.findAll()).thenReturn(allProductEntities);
                }
            }
            return null;
        }).when(productRepository).deleteById(nullable(Long.class));

        return productRepository;
    }

    public static void add(ProductRepository productRepository, ProductEntity productEntity) {
        when(productRepository.existsById(productEntity.getProductId())).thenReturn(true);
        when(productRepository.findById(productEntity.getProductId())).thenReturn(Optional.of(productEntity));
        List<ProductEntity> currentFindAll = productRepository.findAll();
        currentFindAll.add(productEntity);
        when(productRepository.findAll()).thenReturn(currentFindAll);
    }

    public static void remove(ProductRepository productRepository, ProductEntity productEntity) throws NotFoundException {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productEntity.getProductId());
        if (optionalProductEntity.isEmpty()) {
            throw new NotFoundException();
        }
        when(productRepository.existsById(productEntity.getProductId())).thenReturn(false);
        when(productRepository.findById(productEntity.getProductId())).thenReturn(Optional.empty());
        List <ProductEntity> currentFindAll = productRepository.findAll();
        currentFindAll.removeIf( entity -> entity.getProductId().equals(productEntity.getProductId()));
    }
}
