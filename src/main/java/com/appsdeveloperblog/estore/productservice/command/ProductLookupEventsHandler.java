package com.appsdeveloperblog.estore.productservice.command;

import com.appsdeveloperblog.estore.productservice.core.data.ProductLookupEntity;
import com.appsdeveloperblog.estore.productservice.core.data.ProductLookupRepository;
import com.appsdeveloperblog.estore.productservice.core.events.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
@RequiredArgsConstructor
public class ProductLookupEventsHandler {
    // Data fields
    private final ProductLookupRepository productLookupRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductLookupEntity productLookupEntity = new ProductLookupEntity(event.getProductId(), event.getTitle());
        productLookupRepository.save(productLookupEntity);
    }
}
