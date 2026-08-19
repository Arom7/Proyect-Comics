package com.example.comics.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class InventoryRequest {
    @NotNull(message = "El producto es obligatorio.")
    private Long productId;

    @NotNull(message = "La cantidad de ingres/salida/ajuste es requerida")
    @Min(value=1, message = "La cantidad debe ser mayor o igual a 1")
    private int cantidad;

    @NotNull(message = "El motivo del movimiento para un producto es requerido")
    private String motivo;
}
