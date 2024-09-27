package com.appsdeveloperblog.estore.productservice.query.rest;

import com.appsdeveloperblog.estore.productservice.query.FindProductsQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductQueryController {
    private final QueryGateway queryGateway;  // To send the query to the query bus.

    @GetMapping
    public List<ProductRestModel> getProducts() {
        FindProductsQuery findProductsQuery = new FindProductsQuery();
        return queryGateway.query(findProductsQuery, ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
    }
}
