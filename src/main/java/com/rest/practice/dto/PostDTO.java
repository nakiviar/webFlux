package com.rest.practice.dto;

import lombok.Data;

@Data
public class PostDTO {
    private Integer id;
    private Integer userId;
    private String title;
    private String body;
}
