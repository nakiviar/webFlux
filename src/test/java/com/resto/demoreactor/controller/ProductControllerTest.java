package com.resto.demoreactor.controller;


import com.resto.demoreactor.dto.ProductDTO;
import com.resto.demoreactor.model.Product;
import com.resto.demoreactor.service.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Resources;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class) // trae el contexto de spring -> iny dependencias , etc
@WebFluxTest(controllers = ProductController.class)
public class ProductControllerTest {
    //inyectar las dependencias
    @Autowired
    private WebTestClient client; // simula llamados https a tu api sin un servidor real , como si loo estuvieras llamando por postman
    //metodo en donde vamos a mockear los flujos de datos
    @MockBean
    private IProductService service;

    @MockBean
    @Qualifier("defaultMapper")
    private ModelMapper defaultMapper;

    @MockBean
    private Resources resources;

    private List<Product> lista;
    private  Product product1;
    private  ProductDTO productDTO1;
    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        product1= new Product();
        product1.setId(1);
        product1.setName("mando PS3");
        product1.setPrice(32.0);
        product1.setState(false);

        productDTO1= new ProductDTO();
        productDTO1.setId(1);
        productDTO1.setName("mando PS3");
        productDTO1.setPrice(32.0);
        productDTO1.setState(false);


        lista = new ArrayList<>();
        lista.add(product1);
        Mockito.when(service.listAll()).thenReturn(Flux.fromIterable(lista));
        Mockito.when(service.save(any())).thenReturn(Mono.just(product1));

        Mockito.when(defaultMapper.map(product1,ProductDTO.class)).thenReturn(productDTO1);
        Mockito.when(defaultMapper.map(productDTO1,Product.class)).thenReturn(product1);
    }

    @Test
    public void findAllTest(){
        client.get()
                .uri("/product")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void saveProductTest(){
        client.post()
                .uri("/product")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(productDTO1),ProductDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

}
