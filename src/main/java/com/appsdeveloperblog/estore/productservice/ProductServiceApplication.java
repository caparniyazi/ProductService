package com.appsdeveloperblog.estore.productservice;

import com.appsdeveloperblog.estore.core.config.AxonConfig;
import com.appsdeveloperblog.estore.productservice.command.interceptors.CreateProductCommandInterceptor;
import com.appsdeveloperblog.estore.productservice.core.errorhandling.ProductServiceEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
@Import({ AxonConfig.class })
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Autowired
    public void registerCreateProductMethodInterceptor(ApplicationContext context,
                                                       CommandBus commandBus) {
        commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
    }

    // On startup Spring framework will create an EventProcessingConfigurer object and inject here.
    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.registerListenerInvocationErrorHandler(
                "product-group",
                conf -> new ProductServiceEventsErrorHandler());
    }

    @GetMapping("/")
    public String home() {
        return "<h3>Hello World</h3>";
    }
}
