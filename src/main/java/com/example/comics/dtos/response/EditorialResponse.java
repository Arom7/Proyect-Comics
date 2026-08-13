package com.example.comics.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditorialResponse {
    private Long id;
    private String nombre;
    private String descripcion;
}
