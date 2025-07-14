package com.resto.demoreactor.service.impl;


import com.resto.demoreactor.model.Product;
import com.resto.demoreactor.repository.IProductRepository;
import com.resto.demoreactor.service.IProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository repo;

    @Override
    public Flux<Product> listAll() {
        return repo.findAll();
    }

    @Override
    public Mono<Product> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Mono<Product> save(Product product) {
        return repo.save(product);
    }

    @Override
    public Mono<Product> update(Product product, Integer id) {
        return repo.findById(id).flatMap(e-> repo.save(product));
    }

    @Override
    public Mono<Boolean> delete(Integer id) {
        return repo.findById(id)
                .hasElement()
                .flatMap(result -> {
                    if(result){
                        return repo.deleteById(id).thenReturn(true);
                    }else return Mono.just(false);
                });

    }

    @Override
    public Flux<Product> listForPrice(Double price) {
        return repo.listaPreciosBajos(price);
    }


}
