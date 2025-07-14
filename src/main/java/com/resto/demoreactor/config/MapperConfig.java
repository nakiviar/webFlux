package com.resto.demoreactor.config;

import com.resto.demoreactor.dto.ProductDTO;
import com.resto.demoreactor.model.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("defaultMapper")
    public ModelMapper defaultMapper(){
        return new ModelMapper();
    }

    @Bean("productMapper")
    public ModelMapper productMapper(){
        ModelMapper mapper= new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //Convertir un PRODUCTDTO A PRODUCT
        TypeMap<ProductDTO, Product> typeMap1 =mapper.createTypeMap(ProductDTO.class,Product.class);
        typeMap1.addMapping(ProductDTO::getName,(dest,v)-> dest.setName((String) v));
        return mapper;
    }
}
