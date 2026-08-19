package com.example.comics.repository;

import com.example.comics.dtos.response.InventoryResponse;
import com.example.comics.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    // Buscar inventario por id de producto
    Optional<Inventory> findByProductoId(Long productoId);

    //@Query("SELECT i FROM INVENTORY i WHERE i.product_id = :inventory")
    //void updateAmountInventory(@Param("inventoryId") Long inventory , @Param("cantidad") int amount);

    //@Query("SELECT u FROM Usuario u WHERE u.estado = :estado AND u.edad >= :edadMin")
    //List<Usuario> buscarActivosConEdadMinima(@Param("estado") Estado estado, @Param("edadMin") Integer edadMin);
}
