package com.rest.practice.service;

import com.rest.practice.dto.PostDTO;
import com.rest.practice.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    Flux<Product> listar();
    Mono<Product> listarPorId(Integer id);
    Mono<Product> guardar(Product producto);
    Mono<Product> actualizar(Product producto,Integer id);

    Mono<PostDTO> listarPostXId(Integer id);
}
