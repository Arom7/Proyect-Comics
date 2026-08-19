package com.example.comics.service.implementation;

import com.example.comics.dtos.request.InventoryRequest;
import com.example.comics.exceptions.NotFoundException;
import com.example.comics.model.Inventory;
import com.example.comics.model.InventoryMovement;
import com.example.comics.repository.InventoryMovementRepository;
import com.example.comics.repository.InventoryRepository;
import com.example.comics.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import static com.example.comics.model.TypeMovement.ENTRADA;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMovementRepository inventoryMovementRepository;

    @Override
    public void storeInventoryMovement(InventoryRequest request){
        // Busqueda de inventario de un producto
        Inventory inventory = inventoryRepository.findByProductoId(request.getProductId())
                .orElseThrow(()-> new NotFoundException("No existe un inventario de este producto registrado."));

        // Almacenar stock anterior
        int stock_anterior = inventory.getStockActual();
        // Actualizacion de cantidad de stock de un producto
        int amount = stock_anterior + request.getCantidad();
        inventory.setStockActual(amount);
        inventoryRepository.save(inventory);

        // Si no existe entonces registrar uno nuevo
        InventoryMovement newMovement = InventoryMovement.builder()
                .typeMovement(ENTRADA)
                .cantidad(request.getCantidad())
                .stockAnterior(stock_anterior)
                .stockNuevo(amount)
                .inventory(inventory)
                .motivo("Ingreso de mercaderia")
                .createdAt(LocalDateTime.now())
                .build();
        // Registro del nuevo movimiento realizado
        inventoryMovementRepository.save(newMovement);
    }
}
