package com.shubhada.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddNewProductRequestDTO {
    private String title;
    private double price;
    private  String description;
    private String image;
    private String category;
}
