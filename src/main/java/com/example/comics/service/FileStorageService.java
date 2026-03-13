package com.example.comics.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileStorageService {
    // Almacenamiento del documento
    String storeFile(MultipartFile file, Long productoId);
    // Cargar documento
    Resource loadFileAsResource(String filename);
    // Eliminacion de archivo
    void deleteFile(String filename);
    // Verificacion si el archivo esta en formato de imagen
    boolean isImageFile(MultipartFile file);
}
