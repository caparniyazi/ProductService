package com.appsdeveloperblog.estore.productservice.rest;

import com.appsdeveloperblog.estore.productservice.command.CreateProductCommand;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductCommandController {
    // Data fields
    private final Environment env;
    private final CommandGateway commandGateway;

    // Methods

    /**
     * A modifying request is a command in CQRS.
     *
     * @param createProductRestModel The model
     * @return The product
     */
    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel createProductRestModel) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder().price(createProductRestModel.getPrice())
                .title(createProductRestModel.getTitle())
                .quantity(createProductRestModel.getQuantity())
                .productId(UUID.randomUUID().toString()).build();

        try {
            return commandGateway.sendAndWait(createProductCommand);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @PutMapping
    public String updateProdcut() {
        return "Product updated";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "Product deleted";
    }

    @GetMapping
    public String getProducts() {
        // local.server.port will resolve to the real port number the app is running on..
        return "Product list " + env.getProperty("local.server.port");
    }
}
