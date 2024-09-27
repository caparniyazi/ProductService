package com.appsdeveloperblog.estore.productservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;

/**
 * Normally it is a read-only class reading from the model..
 */
@Builder
@Data
@ComponentScan
public class CreateProductCommand {
    // Data fields
    // Axon will use this id to associate this command with an aggregate object in the application.
    // Aggregate is the main object.
    @TargetAggregateIdentifier
    private final String productId;
    private final String title;
    private final BigDecimal price;
    private final int quantity;
}
