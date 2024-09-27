package com.appsdeveloperblog.estore.productservice.query.rest;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductRestModel {
    // Data fields
    private  String productId;
    private  String title;
    private BigDecimal price;
    private  int quantity;
}
