package com.appsdeveloperblog.estore.productservice.rest;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CreateProductRestModel {
    // Data fields
    private String title;
    private BigDecimal price;
    private int quantity;
}
