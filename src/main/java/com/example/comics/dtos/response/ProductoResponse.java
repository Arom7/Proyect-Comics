package com.example.comics.dtos.response;

import com.example.comics.model.TipoProducto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductoResponse {
    private Long id;
    private String titulo;
    private double precio;
    private String editorial;
    private TipoProducto tipo;
    private String mainImage;
}
