package com.appsdeveloperblog.estore.productservice.core.events;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Naming convention is:  <Noun><PerformedAction><Event>
 * For example, ProductCreatedEvent, ProductShippedEvent, ProductDeletedEvent
 * When this event is consumed, we will persist the product info to the database.
 */
@Data
public class ProductCreatedEvent {
    private String productId;
    private String title;
    private BigDecimal price;
    private int quantity;
}
