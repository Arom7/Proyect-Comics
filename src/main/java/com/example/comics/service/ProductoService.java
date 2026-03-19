package com.example.comics.service;

import java.util.List;
import java.util.function.LongFunction;

import com.example.comics.dtos.response.ProductoResponseAdmin;
import com.example.comics.dtos.response.ProductoResponseDetails;
import org.springframework.stereotype.Service;

import com.example.comics.dtos.request.ProductoRequest;
import com.example.comics.dtos.response.ProductoResponse;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ProductoService {
    // Lista de productos para cliente
    List<ProductoResponse> obtenerListaProductos();

    // Lista de productos para administrador
    List<ProductoResponseAdmin> obtenerProductosAdmin();

    // Registrar un nuevo producto
    ProductoResponse crearProducto(ProductoRequest productoRequest, List<MultipartFile> imagenes);

    // Busqueda por Id
    ProductoResponseDetails obtenerPorId(Long id);

    //ProductoResponse obtenerPorNombre(String nombre);

    // Eliminacion logica de un producto
    void borrarProducto(Long id);
}