package com.rest.practice.configuration;

import com.rest.practice.handler.ProductHandler;
import com.rest.practice.model.Product;
import com.rest.practice.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class routerConfig {

    private final IProductService service;

    @Bean
    public RouterFunction<ServerResponse> routes (ProductHandler productHandler){
        return RouterFunctions.route(RequestPredicates.GET("/v2"),s->ServerResponse
                        .ok()
                        .body(service.listar(),Product.class))
                .andRoute(RequestPredicates.POST("/v23"),productHandler::guardar);
    }

}
