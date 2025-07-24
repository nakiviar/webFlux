package com.rest.practice.controller;

import com.rest.practice.dto.PostDTO;
import com.rest.practice.dto.ProductDTO;
import com.rest.practice.model.Product;
import com.rest.practice.service.IProductService;
import io.r2dbc.spi.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.PortUnreachableException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final IProductService service;

    @Qualifier("productMapper")
    private final ModelMapper productMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<ProductDTO>>> listar(){
       Flux<ProductDTO> lista = service.listar().map(this::convertirADTO);
       return Mono.just(ResponseEntity.ok()
                       .contentType(MediaType.APPLICATION_JSON)
                       .body(lista))
                       .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PostDTO>> listPosts(@PathVariable Integer id){
        return service.listarPostXId(id).map( p -> ResponseEntity.ok(p)).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ProductDTO>> guardar(@RequestBody ProductDTO product){
        return service.guardar(convertirAEntity(product))
                .map(this::convertirADTO)
                .map(p->ResponseEntity
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public  Mono<ResponseEntity<ProductDTO>> actualizar(@RequestBody ProductDTO product, @PathVariable Integer id){
        return Mono.just(product)
                .map(productDTO -> {
                    productDTO.setId(id);
                    return productDTO;
                })
                .map(this::convertirAEntity)
                .flatMap( p -> service.actualizar(p,id))
                .map(e->ResponseEntity.ok(convertirADTO(e) ))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    private ProductDTO convertirADTO(Product p) {
        return productMapper.map(p,ProductDTO.class);
    }
    private Product convertirAEntity(ProductDTO p){
        return productMapper.map(p,Product.class);
    }


}
