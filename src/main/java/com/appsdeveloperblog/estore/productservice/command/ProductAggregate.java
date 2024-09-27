package com.appsdeveloperblog.estore.productservice.command;

import com.appsdeveloperblog.estore.core.commands.ReserveProductCommand;
import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;
import com.appsdeveloperblog.estore.productservice.core.events.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * Not a regular class, but rather it's an aggregate.
 * It must have a default constructor so that Axon can create and initialize the aggregate instance,
 * and the other constructor will be used as a command handler method, and will
 * be used when the createProductCommand() is dispatched.
 * <p/>
 * Axon use  @AggregateIdentifier and @TargetAggregateIdentifier to associate the command with the aggregate.
 */
@Aggregate
public class ProductAggregate {
    // Data fields
    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private int quantity;

    public ProductAggregate() {
    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand) {
        if (quantity < reserveProductCommand.getQuantity()) {
            throw new IllegalArgumentException("Insufficient quantity in stock");
        }

        ProductReservedEvent productReservedEvent = ProductReservedEvent.builder()
                .productId(reserveProductCommand.getProductId())
                .quantity(reserveProductCommand.getQuantity())
                .orderId(reserveProductCommand.getOrderId())
                .userId(reserveProductCommand.getUserId())
                .build();

        // Apply the event to the aggregate and handle it with event sourcing handler..
        AggregateLifecycle.apply(productReservedEvent);
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        // Validate CreateProductCommand
        if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        if (createProductCommand.getTitle() == null || createProductCommand.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        // Create the event
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        // Apply: It will dispatch the event to all event handlers inside this aggregate.
        // So that the state of this aggregate can be updated with new information.
        // Only after the productCreatedEvent has been applied to this aggregate,
        // then this event will be scheduled for publication to other event handlers and
        // persisted to event-store.
        AggregateLifecycle.apply(productCreatedEvent);
    }

    /**
     * Event sourcing method.
     * It will be used to initialize the current state of the aggregate with the latest information.
     * This method should only be used for method state, and NO business logic should be added!
     *
     * @param productCreatedEvent The Product Created Event.
     */
    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.title = productCreatedEvent.getTitle();
        this.quantity = productCreatedEvent.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent) {
        this.quantity -= productReservedEvent.getQuantity();

    }
}
