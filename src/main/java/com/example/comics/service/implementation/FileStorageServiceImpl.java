package com.example.comics.service.implementation;

import com.example.comics.config.FileStorageProperties;
import com.example.comics.exceptions.FileStorageException;
import com.example.comics.service.FileStorageService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final FileStorageProperties fileStorageProperties;
    private Path fileStorageLocation;

    @PostConstruct
    public void init(){
        log.info("Iniciando el servicio ... : {}" ,  fileStorageProperties.getUrl());

        String url = fileStorageProperties.getUrl();

        this.fileStorageLocation = Paths.get(url)
                .toAbsolutePath()
                .normalize();
        // Creacion de directorio fisicamente
        try{
            Files.createDirectories(this.fileStorageLocation);
            log.info("Generada la ubicacion");
        }catch (Exception e){
            throw new FileStorageException("No se pudo crear el directorio.", e);
        }
    }

    public String storeFile(MultipartFile file, Long productoId) {
        if(file.isEmpty()){
            throw new FileStorageException("El archivo no puede ser vacio");
        }

        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try{
            // Validar nombre
            if (originalFilename.contains("..")) {
                throw new FileStorageException("El nombre del archivo contiene secuencia inválida: " + originalFilename);
            }

            // Generar nombre unico
            String extension = getFileExtension(originalFilename);
            String uniqueFilename = productoId + "_" + UUID.randomUUID() + extension;

            // Crear subdirectorio por producto
            Path productDirectory = this.fileStorageLocation.resolve(productoId.toString());
            Files.createDirectories(productDirectory);

            // Guardar archivo
            Path targetLocation = productDirectory.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Ruta relativa donde se guardo en BD
            return productoId + "/" + uniqueFilename;
        }catch (Exception e){
            throw new FileStorageException("No se puede guardar el archivo " + originalFilename, e);
        }
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException("Archivo no encontrado: " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("Archivo no encontrado: " + filename, ex);
        }
    }

    public void deleteFile(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Files.deleteIfExists(filePath);
            log.info("Archivo eliminado: {}", filePath);
        } catch (Exception ex) {
            throw new FileStorageException("No se pudo eliminar el archivo: " + filename, ex);
        }
    }

    public boolean isImageFile(MultipartFile file) {
        if(file ==  null || file.isEmpty()){
            return false;
        }

        String contentType = file.getContentType();

        if (contentType != null && contentType.startsWith("image/")) {
            return true;
        }

        // Verificacion por extension
        String filename = file.getOriginalFilename();
        if (filename != null) {
            String extension = filename.toLowerCase();
            return extension.endsWith(".jpg") ||
                    extension.endsWith(".jpeg") ||
                    extension.endsWith(".png") ||
                    extension.endsWith(".gif") ||
                    extension.endsWith(".webp") ||
                    extension.endsWith(".bmp") ||
                    extension.endsWith(".svg");
        }

        return false;
    }

    // ==========================================
    // MÉTODOS PRIVADOS AUXILIARES
    // ==========================================

    private String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex);
    }
}
