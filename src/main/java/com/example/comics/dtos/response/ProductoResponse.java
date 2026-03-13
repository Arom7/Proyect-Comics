package com.example.comics.dtos.response;

import java.time.LocalDate;
import java.util.List;

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
    private String descripcion;
    private double precio;
    private String editorial;
    private String autor;
    private TipoProducto tipo;
    private LocalDate fechaPublicacion;
    private List<ImagenResponse> images;
}
