package com.appsdeveloperblog.estore.productservice.command.rest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class CreateProductRestModel {
    // Data fields
    @NotBlank(message = "Product title is required")
    private String title;

    @Min(value = 1, message = "Price cannot be lower than 1")
    private BigDecimal price;

    @Min(value = 1, message = "Price cannot be lower than 1")
    @Max(value = 5, message = "Price cannot be greater than 5")
    private int quantity;
}
