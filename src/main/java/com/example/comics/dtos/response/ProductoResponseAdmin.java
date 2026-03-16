package com.example.comics.dtos.response;

import com.example.comics.model.TipoProducto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductoResponseAdmin {
    private long id;
    private String titulo;
    private double precio;
    private TipoProducto tipo;
    // Agregar el stock y su disponibilidad.
}
