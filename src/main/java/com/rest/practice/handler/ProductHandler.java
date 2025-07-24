package com.rest.practice.handler;

import com.rest.practice.model.Product;
import com.rest.practice.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final IProductService service;

    public Mono<ServerResponse> guardar(ServerRequest serverRequest){
        return serverRequest.bodyToMono(Product.class)
                .flatMap(p->service.guardar(p))
                .flatMap(p-> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p));
    }
}
