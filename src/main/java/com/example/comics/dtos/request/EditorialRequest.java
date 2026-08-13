package com.example.comics.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditorialRequest {
    @NotBlank(message = "El titulo es obligatorio")
    @Size(min = 3 , max = 100 , message = "El nombre de la editorial debe contener entre 3 a 100 caracteres.")
    private String nombre;

    @Size(max = 900 , message = "La descripcion de la editorial debe contener como maximo 900 caracteres.")
    private String descripcion;
}
