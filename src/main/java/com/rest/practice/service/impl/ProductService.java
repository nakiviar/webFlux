package com.rest.practice.service.impl;

import com.rest.practice.dto.PostDTO;
import com.rest.practice.model.Product;
import com.rest.practice.repository.IProductRepository;
import com.rest.practice.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository repo;

    private final WebClient client;

    @Override
    public Flux<Product> listar() {
        return repo.findAll();
    }

    @Override
    public Mono<Product> listarPorId(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Mono<Product> guardar(Product producto) {
        return repo.save(producto);
    }

    @Override
    public Mono<Product> actualizar(Product producto, Integer id) {
        return repo.findById(id).flatMap(p->repo.save(producto));
    }

    @Override
    public Mono<PostDTO> listarPostXId(Integer id) {
        return client
                .get()
                .uri("/posts/{id}",id)
                .retrieve()
                .bodyToMono(PostDTO.class);
    }
}
