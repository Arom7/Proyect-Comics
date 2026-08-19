package com.example.comics.repository;

import com.example.comics.model.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryMovementRepository extends JpaRepository <InventoryMovement, Long > {
}
