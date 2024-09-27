package com.appsdeveloperblog.estore.productservice.core.errorhandling;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

import javax.annotation.Nonnull;

public class ProductServiceEventsErrorHandler implements ListenerInvocationErrorHandler {
    /**
     * Method to allow us handle the exceptions thrown in the event handler methods.
     * @param exception The exception
     * @param event The event
     * @param eventHandler
     * @throws Exception The exception
     */
    @Override
    public void onError(@Nonnull Exception exception,
                        @Nonnull EventMessage<?> event,
                        @Nonnull EventMessageHandler eventHandler) throws Exception {
        throw exception;    // is the last throwing  to rollback the transaction.
    }
}
