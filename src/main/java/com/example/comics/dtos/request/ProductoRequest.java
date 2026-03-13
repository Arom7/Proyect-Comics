package com.example.comics.dtos.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductoRequest {
    
    @NotBlank(message = "El titulo es obligatorio")
    @Size(min = 3 , max = 100 , message = "El titulo debe contener entre 3 a 100 caracteres.")
    private String titulo;

    @Size(max = 900 , message = "La descripcion debe contener como maximo 900 caracteres.")
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0 , message = "El precio debe ser mayor o igual a 0")
    private double precio;

    private String editorial;
    private String autor;
    
    @NotNull(message = "El tipo de producto es obligatorio")
    private String tipo;

    @PastOrPresent(message = "La fecha de publicacion no puede ser futura.")
    private LocalDate fechaPublicacion;
}
