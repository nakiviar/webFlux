package com.resto.demoreactor.handler;

import com.resto.demoreactor.model.Product;
import com.resto.demoreactor.service.impl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {
    private final ProductService service;

    public Mono<ServerResponse> save(ServerRequest serverRequest){
        return serverRequest.bodyToMono(Product.class)
                .flatMap(product -> service.save(product))
                .flatMap(p -> ServerResponse.status(201).bodyValue(p));
    }
}
