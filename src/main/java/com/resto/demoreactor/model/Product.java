package com.resto.demoreactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table("products")
public class Product {

    @Id
    private Integer id;
    @Column("name")
    private String name;
    @Column("price")
    private Double price;
    @Column("state")
    private Boolean state;

}
