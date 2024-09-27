package com.appsdeveloperblog.estore.productservice.query;

import com.appsdeveloperblog.estore.productservice.core.data.ProductEntity;
import com.appsdeveloperblog.estore.productservice.core.data.ProductRepository;
import com.appsdeveloperblog.estore.productservice.query.rest.ProductRestModel;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductsQueryHandler {
    // Data fields
    private final ProductRepository productRepository;

    @QueryHandler
    public List<ProductRestModel> findProducts(FindProductsQuery findProductsQuery) {
        List<ProductRestModel> productsRest = new ArrayList<>();
        List<ProductEntity> storedProducts = productRepository.findAll();

        for (ProductEntity productEntity : storedProducts) {
            ProductRestModel productRestModel = new ProductRestModel();
            BeanUtils.copyProperties(productEntity, productRestModel);
            productsRest.add(productRestModel);
        }
        return productsRest;
    }
}
