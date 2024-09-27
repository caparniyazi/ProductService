package com.appsdeveloperblog.estore.productservice.query;

import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;
import com.appsdeveloperblog.estore.productservice.core.data.ProductEntity;
import com.appsdeveloperblog.estore.productservice.core.data.ProductRepository;
import com.appsdeveloperblog.estore.productservice.core.events.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group")
public class ProductEventsHandler {
    // Data fields
    private final ProductRepository productRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsHandler.class);

    @ExceptionHandler(value = Exception.class)
    public void handle(Exception exception) throws Exception {
        throw exception;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public void handle(IllegalArgumentException e) {
        // Log error message
    }

    /**
     * The handler that is responsible for persisting product details.
     *
     * @param event The event
     */
    @EventHandler
    public void on(ProductCreatedEvent event) throws Exception {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);

        try {
            productRepository.save(productEntity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        //throw new Exception("Forcing the exception in the Event Handler class.");
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) {
        ProductEntity productEntity = productRepository.findByProductId(productReservedEvent.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
        productRepository.save(productEntity);

        LOGGER.info("ProductReservedEvent is called for productId: " + productReservedEvent.getProductId()
                + " and orderId:" + productReservedEvent.getOrderId());
    }
}
