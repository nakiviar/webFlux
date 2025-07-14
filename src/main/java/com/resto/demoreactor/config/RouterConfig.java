package com.resto.demoreactor.config;


import com.resto.demoreactor.Handler.ProductHandler;
import com.resto.demoreactor.model.Product;
import com.resto.demoreactor.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import javax.print.DocFlavor;

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
