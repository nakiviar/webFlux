package com.resto.demoreactor.service;

import com.resto.demoreactor.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IProductService {

    Flux<Product> listAll();
    public Mono<Product> findById(Integer id);
    public Mono<Product> save(Product product);
    public Mono<Product> update(Product product, Integer id);
    public Mono<Boolean> delete(Integer id);

    Flux<Product> listForPrice(Double price);
}
