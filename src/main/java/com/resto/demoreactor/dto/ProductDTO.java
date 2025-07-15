package com.resto.demoreactor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data // getters, setters, toString(), y constructor con los campos que sean etiquetados con final o no nulos
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // se muestran solo los que no estan vacios ni nulos
public class ProductDTO {

    private Integer id;
    @NotNull
    private String name;
    @Min(value = 1)
    @Max(value = 999)
    private Double price;
    private Boolean state;
}
