package com.example.comics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.comics.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
}
