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
@RequestMapping("/products")
@AllArgsConstructor
@Slf4j

public class ProductoController {
    
    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> getAllProducts() {
        List<ProductoResponse> response = productoService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<List<ProductoResponseAdmin>>> getAllProductsByAdmin(){
        List<ProductoResponseAdmin> response = productoService.getAllProductsByAdmin();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponseDetails>> getProductById(@PathVariable Long id) {
        ProductoResponseDetails response = productoService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoResponse>> crearProducto(
            @RequestPart @Valid ProductoRequest product,
            @RequestPart("files") List<MultipartFile> images
    ) {
        log.info("Realizando el registro correspondiente del producto.");
        ProductoResponse response = productoService.crearProducto(product, images);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Producto registrado de manera exitosa.",response));
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<ProductoResponse>> store(
            @RequestBody @Valid ProductoRequest product
    ){
        ProductoResponse response = productoService.storeProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/testing/two")
    public ResponseEntity<ApiResponse<String>> realizarTesting() {
        return ResponseEntity.ok(ApiResponse.success("Prueba de testing correcta."));
    }
}
