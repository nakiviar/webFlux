package com.resto.demoreactor.repository;

import com.resto.demoreactor.model.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

public interface IProductRepository extends ReactiveCrudRepository <Product, Integer>{
    @Query("SELECT * FROM products WHERE price > :price")
    Flux<Product> listaPreciosBajos(Double price);
}
