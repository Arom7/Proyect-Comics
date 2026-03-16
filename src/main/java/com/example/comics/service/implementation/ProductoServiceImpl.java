package com.example.comics.service.implementation;

import java.util.Arrays;
import java.util.List;

import com.example.comics.model.Image;
import com.example.comics.model.TipoProducto;
import com.example.comics.service.ImagenService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.comics.dtos.request.ProductoRequest;
import com.example.comics.dtos.response.ProductoResponse;
import com.example.comics.mapper.ProductoMapper;
import com.example.comics.model.Producto;
import com.example.comics.repository.ProductoRepository;
import com.example.comics.service.ProductoService;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService{
    
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ImagenService imagenService;

    @Override
    public List<ProductoResponse> listarProductos() {
        return productoRepository.findAll().stream().map(productoMapper::productoToResponse).toList();
    }

    @Override
    @Transactional
    public ProductoResponse crearProducto(ProductoRequest productoRequest, List<MultipartFile> imagenes) {
        Producto producto = productoMapper.requestToProducto(productoRequest);

        validarYEstablecerTipo(producto , productoRequest.getTipo());

        producto = productoRepository.save(producto);

        if (imagenes != null && !imagenes.isEmpty()) {
            List<Image> imagenesAlmacenadas = imagenService.subirImagenes(
                    producto.getId(),
                    imagenes
            );

            if (imagenesAlmacenadas != null && !imagenesAlmacenadas.isEmpty()) {
                imagenesAlmacenadas.forEach(producto::agregarImagen);
            }
        }

        return productoMapper.productoToResponse(producto);
    }

    @Override
    public ProductoResponse obtenerPorId(Long id) {
        return productoMapper.productoToResponse(productoRepository.getReferenceById(id));
    }

    @Override
    public void borrarProducto(Long id) {
        Producto producto = productoRepository.getReferenceById(id);
        // Eliminacion correcta de imagenes.
        List<Image> imagenes = producto.getImages();
        for (Image imagen : imagenes) {
            producto.removerImagen(imagen);
        }
        productoRepository.delete(producto);
    }

    // ================================
    //      Metodos auxiliares
    // ================================
    private void validarYEstablecerTipo(Producto producto, String tipoStr) {
        if (tipoStr == null || tipoStr.isBlank()) {
            throw new IllegalArgumentException("El tipo de producto es obligatorio");
        }

        try {
            TipoProducto tipo = TipoProducto.valueOf(tipoStr.toUpperCase().trim());
            producto.setTipo(tipo);
        } catch (IllegalArgumentException e) {
            String mensaje = String.format(
                    "Tipo inválido: '%s'. Valores permitidos: %s",
                    tipoStr,
                    Arrays.toString(TipoProducto.values())
            );
            throw new IllegalArgumentException(mensaje);
        }
    }
}
