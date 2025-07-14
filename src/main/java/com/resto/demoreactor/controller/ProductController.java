package com.resto.demoreactor.controller;

import com.resto.demoreactor.dto.ProductDTO;
import com.resto.demoreactor.model.Product;
import com.resto.demoreactor.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<Product>>> listar(){
        Flux<Product> listaFiltrada = service.listAll()
                .map(product -> {
                   product.setStatus(false);
                   return product;
                })
                .filter(p-> p.getPrice()>10);

        return Mono.just(ResponseEntity
                .ok(listaFiltrada))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/ls")
    public Mono<ResponseEntity <Flux<Product>>> listarPreciosBajos(){
        return Mono.just( ResponseEntity
                .status(HttpStatus.OK)
                .body(service.listForPrice(10.0))
                );
    }
//    @PostMapping
//    public Mono<ResponseEntity<Product>> guardar(@RequestBody Product product, final ServerHttpRequest req){
//        return service.save(product)
//                .map(e -> ResponseEntity.created(
//                                        URI.create(req.getURI().toString().concat("/").concat(e.getId().toString()))
//                                )
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .body(e)
//                )
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getById(@PathVariable("id") int id){
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

//    @PutMapping("/{id}")
//    public Mono<Product> updateProduct(@PathVariable int id,@RequestBody Product product){
//        return service.update(product, id);
//    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable int id,@RequestBody Product product){
        return Mono.just(product)
                .map(p -> {
                    p.setId(id);
                    return  p;
                })
                .flatMap(p-> service.update(p,id))
                .map(p -> ResponseEntity
                        .ok()
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ProductDTO>> guardar(@Valid @RequestBody ProductDTO product){
        return service.save(convertirEnEntity(product))
                .map(prod -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(convertirEnDTO(prod)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
//                .onErrorResume(error -> Mono.just(
//                        ResponseEntity
//                                .status(HttpStatus.BAD_REQUEST)
//                                .body(null))
//                ); // ResponseEntity<Product>

//                .onErrorResume(e -> {
//                    return Mono.error( new RuntimeException("ni modos"));});

    }

    private ProductDTO convertirEnDTO(Product prod) {
        return mapper.map(prod,ProductDTO.class);
    }

    private Product convertirEnEntity(ProductDTO product) {
        return mapper.map(product,Product.class);
    }

    @DeleteMapping("/{id}")
    public Mono<Boolean> deleteById(@PathVariable("id") int id){
        return service.delete(id);
    }

}
