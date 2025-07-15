package com.resto.demoreactor.service.impl;


import com.resto.demoreactor.dto.external.PostDTO;
import com.resto.demoreactor.model.Product;
import com.resto.demoreactor.repository.IProductRepository;
import com.resto.demoreactor.service.IProductService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository repo;
    private final WebClient webClient;

    private final CircuitBreakerRegistry circuitBreakerRegistry;

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

    @Override
    public Mono<PostDTO> getPostById(Integer id) {
        CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("productService");
        return webClient.get()
                .uri("/postsz/{id}",id)
                .retrieve()
                .bodyToMono(PostDTO.class)
                .transformDeferred(CircuitBreakerOperator.of(cb))
                .onErrorResume(throwable -> {
                    System.out.println("Fallo la llamada, usando fallback: " + throwable.getMessage());

                    // Retornar un valor por defecto
                    PostDTO fallbackPost = new PostDTO();
                    fallbackPost.setId(id);
                    fallbackPost.setTitle("Servicio no disponible");
                    fallbackPost.setBody("Este post viene del fallback:)");
                    return Mono.just(fallbackPost);
                });
    }


}
