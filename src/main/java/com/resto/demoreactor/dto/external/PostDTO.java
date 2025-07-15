package com.resto.demoreactor.dto.external;

import lombok.Data;

@Data
public class PostDTO {
    private Integer id;
    private Integer userId;
    private String title;
    private String body;
}
