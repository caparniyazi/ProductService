package com.appsdeveloperblog.estore.productservice.command.interceptors;

import com.appsdeveloperblog.estore.productservice.command.CreateProductCommand;
import com.appsdeveloperblog.estore.productservice.core.data.ProductLookupEntity;
import com.appsdeveloperblog.estore.productservice.core.data.ProductLookupRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    // Data fields
    private final ProductLookupRepository productLookupRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>>
    handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            LOGGER.info("Intercepted command: " + command.getPayloadType());

            if (command.getPayloadType().equals(CreateProductCommand.class)) {
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();

                ProductLookupEntity productLookupEntity =
                        productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(), createProductCommand.getTitle());

                if (productLookupEntity != null) {
                    throw new IllegalStateException(
                            String.format("Product with productId %s or title %s already exists",
                                    createProductCommand.getProductId(), createProductCommand.getTitle()));
                }
            }
            return command;
        };
    }
}
