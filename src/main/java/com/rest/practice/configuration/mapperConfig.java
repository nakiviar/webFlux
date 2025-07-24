package com.rest.practice.configuration;

import com.rest.practice.dto.ProductDTO;
import com.rest.practice.model.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // La anotación @Configuration en Spring indica que una clase contiene definiciones de beans que deben ser gestionados por el contenedor de Spring.
public class mapperConfig {

    @Bean("defaultMapper")
    public ModelMapper defaultMapper(){ return new ModelMapper();};

    @Bean("productMapper")
    public ModelMapper productMapper(){
        ModelMapper modelMapper = new ModelMapper();

        //se refiere a la configuración de cómo ModelMapper hace el mapeo entre objetos
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //mapeando manualmente
        // PRODUCTDTO A PRODUCT
        TypeMap<ProductDTO, Product> typeMap1=modelMapper.createTypeMap(ProductDTO.class,Product.class);
        typeMap1.addMapping(ProductDTO::getNombre,(dest,v)->dest.setName((String) v));
        typeMap1.addMapping(ProductDTO::getPrice,(dest,v)->dest.setPrice((Double) v));
        typeMap1.addMapping(ProductDTO::getStatus,(dest,v)->dest.setStatus((Boolean) v));

        // PRODUCT A PRODUCTDTO
        TypeMap<Product,ProductDTO> typeMap2=modelMapper.createTypeMap(Product.class,ProductDTO.class);
        typeMap2.addMapping(Product::getName,(dest,v)->dest.setNombre((String) v));
        typeMap2.addMapping(Product::getPrice,(dest,v)->dest.setPrice((Double) v));
        typeMap2.addMapping(Product::getStatus,(dest,v)->dest.setStatus((Boolean) v));
        return modelMapper;
    }


}
