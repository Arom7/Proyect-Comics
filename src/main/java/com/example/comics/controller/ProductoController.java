package com.example.comics.controller;

import java.util.List;

import com.example.comics.dtos.response.ApiResponse;
import com.example.comics.dtos.response.ProductoResponseAdmin;
import com.example.comics.dtos.response.ProductoResponseDetails;
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> obtenerListaProductos() {
        List<ProductoResponse> response = productoService.obtenerListaProductos();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<List<ProductoResponseAdmin>>> obtenerProductoAdmin(){
        List<ProductoResponseAdmin> response = productoService.obtenerProductosAdmin();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponseDetails>> obtenerProducto(@PathVariable Long id) {
        ProductoResponseDetails response = productoService.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoResponse>> crearProducto(
            @RequestPart @Valid ProductoRequest request,
            @RequestPart("files") List<MultipartFile> imagenes
    ) {
        log.info("Realizando el registro correspondiente del producto.");
        ProductoResponse response = productoService.crearProducto(request, imagenes);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Producto registrado de manera exitosa.",response));
    }
}
