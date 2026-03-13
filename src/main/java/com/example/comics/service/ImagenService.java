package com.example.comics.service;

import com.example.comics.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ImagenService {
    // Registrar una imagen con opcion a ser principal
    Image subirImagen(Long productoId , MultipartFile file , Boolean esPrincipal);

    // Registrar una lista de imagenes
    List<Image> subirImagenes(Long productoId , List<MultipartFile> imagenes);
}
