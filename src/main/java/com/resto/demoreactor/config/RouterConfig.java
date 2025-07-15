package com.resto.demoreactor.config;


import com.resto.demoreactor.handler.ProductHandler;
import com.resto.demoreactor.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Autowired
    private ProductService service;

    @Bean
    public RouterFunction<ServerResponse> routes(ProductHandler productHandler) {
       // return RouterFunctions.route(RequestPredicate.GET)
        return route(POST("/api/saved").or(POST("/api/save")), productHandler::save);
    }
}
