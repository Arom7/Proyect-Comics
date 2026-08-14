package com.example.comics.service;

import java.util.List;

import com.example.comics.dtos.response.ProductoResponseAdmin;
import com.example.comics.dtos.response.ProductoResponseDetails;
import org.springframework.stereotype.Service;

import com.example.comics.dtos.request.ProductoRequest;
import com.example.comics.dtos.response.ProductoResponse;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ProductoService {
    // Lista de productos para cliente
    List<ProductoResponse> getAllProducts();

    // Lista de productos para administrador
    List<ProductoResponseAdmin> getAllProductsByAdmin();

    // Busqueda por Id
    ProductoResponseDetails getProductById(Long id);

    // Registrar un nuevo producto con imagenes
    ProductoResponse storeProduct(ProductoRequest productoRequest, List<MultipartFile> imagenes);

    //ProductoResponse obtenerPorNombre(String nombre);

    // Eliminacion logica de un producto
    void borrarProducto(Long id);
}