package com.example.comics.service.implementation;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.example.comics.dtos.response.ProductoResponseAdmin;
import com.example.comics.dtos.response.ProductoResponseDetails;
import com.example.comics.model.*;
import com.example.comics.repository.EditorialRepository;
import com.example.comics.repository.InventoryRepository;
import com.example.comics.service.ImagenService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.comics.dtos.request.ProductoRequest;
import com.example.comics.dtos.response.ProductoResponse;
import com.example.comics.mapper.ProductoMapper;
import com.example.comics.repository.ProductoRepository;
import com.example.comics.service.ProductoService;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService{
    
    private final ProductoRepository productoRepository;
    private final EditorialRepository editorialRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductoMapper productoMapper;
    private final ImagenService imagenService;

    @Override
    public List<ProductoResponse> getAllProducts() {
        return productoRepository.findAll().stream().map(productoMapper::productoToResponse).toList();
    }

    @Override
    public List<ProductoResponseAdmin> getAllProductsByAdmin() {
        return productoRepository.findAll().stream().map(productoMapper::productoToAdminResponse).toList();
    }

    @Override
    public ProductoResponseDetails getProductById(Long id) {
        return productoMapper.productoToDetailsResponse(productoRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public ProductoResponse storeProduct(ProductoRequest productoRequest, List<MultipartFile> imagenes) {

        // Busqueda de editorial y control de errores
        Editorial editorial = editorialRepository.findById(productoRequest.getEditorial())
                .orElseThrow(() -> new EntityNotFoundException("Editorial no encontrada."));
        // Mapeo de producto y preparacion de estructura para el registro
        Producto producto = productoMapper.requestToProducto(productoRequest);
        validarYEstablecerTipo(producto , productoRequest.getTipo());
        producto.setEditorial(editorial);
        // Registro del producto
        producto = productoRepository.save(producto);
        // Aplicacion de registro de imagenes
        if (imagenes != null && !imagenes.isEmpty()) {
            List<Image> imagenesAlmacenadas = imagenService.subirImagenes(
                    producto.getId(),
                    imagenes
            );
            if (imagenesAlmacenadas != null && !imagenesAlmacenadas.isEmpty()) {
                imagenesAlmacenadas.forEach(producto::agregarImagen);
            }
        }
        //Registro de un inventario nuevo para el producto
        Inventory newInventory = Inventory.builder()
                .producto(producto)
                .stockActual(0)
                .stockMinimo(10)
                .createdAt(LocalDateTime.now())
                .build();
        inventoryRepository.save(newInventory);

        return productoMapper.productoToResponse(producto);
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
