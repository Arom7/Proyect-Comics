package com.example.comics.mapper;

import com.example.comics.dtos.response.ProductoResponseAdmin;
import com.example.comics.dtos.response.ProductoResponseDetails;
import com.example.comics.model.Image;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.comics.dtos.request.ProductoRequest;
import com.example.comics.dtos.response.ProductoResponse;
import com.example.comics.model.Producto;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring"
)
public interface ProductoMapper {

    // Mapping de producto a response (Cliente)
    ProductoResponse productoToResponse(Producto producto);
    // Imprimir solamente la imagen principal
    @AfterMapping
    default void fillMainImage(Producto producto, @MappingTarget ProductoResponse productoResponse) {
        String url = producto.getImages() == null ? null :
                producto.getImages().stream()
                        .filter(Image::getEsPrincipal)
                        .map(Image::getPath)
                        .findFirst().orElse(null);

        productoResponse.setMainImage(url);
    }

    // Mapping de producto a response (Admin)
    ProductoResponseAdmin productoToAdminResponse(Producto producto);

    // Mapping de producto en response (getId -> respuesta con mayor detalle)
    ProductoResponseDetails productoToDetailsResponse(Producto producto);

    // Mapping de request a producto
    @Mapping(target = "tipo" , ignore = true)
    @Mapping(target = "images" , ignore = true)
    @Mapping(target = "createdAt" , expression = "java(java.time.LocalDateTime.now())")
    Producto requestToProducto(ProductoRequest productoRequest);
}
