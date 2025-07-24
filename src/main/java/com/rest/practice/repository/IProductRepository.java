package com.rest.practice.repository;

import com.rest.practice.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IProductRepository extends ReactiveCrudRepository<Product,Integer> {
}
