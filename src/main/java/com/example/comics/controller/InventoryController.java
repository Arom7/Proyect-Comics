package com.example.comics.controller;

import com.example.comics.dtos.request.InventoryRequest;
import com.example.comics.dtos.response.ApiResponse;
import com.example.comics.service.InventoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventary")
@AllArgsConstructor
@Slf4j

public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> storeInventoryMovement(
            @RequestBody @Valid InventoryRequest request
    ){
        log.info("Ingreso en el registro de movimiento de un producto.");
        inventoryService.storeInventoryMovement(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registro de movimiento realizado de manera exitosa."));
    }
}
