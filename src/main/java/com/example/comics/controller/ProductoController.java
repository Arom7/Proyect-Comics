package com.example.comics.controller;

import java.util.List;

import com.example.comics.dtos.response.ApiResponse;
import com.example.comics.service.ImagenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.comics.dtos.request.ProductoRequest;
import com.example.comics.dtos.response.ProductoResponse;
import com.example.comics.service.ProductoService;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/productos")
@AllArgsConstructor
@Slf4j

public class ProductoController {
    
    private final ProductoService productoService;
    private final ImagenService imagenService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> listarProductos() {
        List<ProductoResponse> response = productoService.listarProductos();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoResponse>> crearProducto(
            @RequestPart @Valid ProductoRequest request,
            @RequestPart("files") List<MultipartFile> imagenes
    ) {
        log.info("Ingresando al proyecto de creacion");
        ProductoResponse response = productoService.crearProducto(request, imagenes);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Producto registrado de manera exitosa.",response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponse>> obtenerProducto(@PathVariable Long id) {
        ProductoResponse response = productoService.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
