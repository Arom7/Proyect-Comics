package com.example.comics.service;

import com.example.comics.dtos.request.InventoryRequest;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {
    // Registro de movimientos de un producto en inventario
    void storeInventoryMovement(InventoryRequest request);
}
