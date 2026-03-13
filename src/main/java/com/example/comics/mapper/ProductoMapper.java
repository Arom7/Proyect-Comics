package com.example.comics.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.comics.dtos.request.ProductoRequest;
import com.example.comics.dtos.response.ProductoResponse;
import com.example.comics.model.Producto;

@Mapper(
        componentModel = "spring"
)
public interface ProductoMapper {

    // Mapping de producto a response
    ProductoResponse productoToResponse(Producto producto);

    // Mapping de request a producto
    @Mapping(target = "tipo" , ignore = true)
    @Mapping(target = "images" , ignore = true)
    Producto requestToProducto(ProductoRequest productoRequest);
}
