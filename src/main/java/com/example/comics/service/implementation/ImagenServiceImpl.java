package com.example.comics.service.implementation;

import com.example.comics.exceptions.FileStorageException;
import com.example.comics.model.Image;
import com.example.comics.model.Producto;
import com.example.comics.repository.ProductoRepository;
import com.example.comics.service.FileStorageService;
import com.example.comics.service.ImagenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ImagenServiceImpl implements ImagenService {
    private final FileStorageService fileStorageServiceImpl;
    private final ProductoRepository productoRepository;

    @Override
    public Image subirImagen(Long productoId ,MultipartFile file, Boolean esPrincipal){

        Producto producto = productoRepository.getReferenceById(productoId);

        if (file.isEmpty()) {
            throw new FileStorageException("El archivo está vacío");
        }

        long maxSize = 5 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new FileStorageException("El archivo es muy grande. Máximo: 5MB");
        }

        if(!fileStorageServiceImpl.isImageFile(file)){
            throw new FileStorageException("El archivo debe ser una imagen (JPG, PNG, GIF, etc.)");
        }

        String rutaRelativa = fileStorageServiceImpl.storeFile(file, productoId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/productos/")
                .path(rutaRelativa)
                .toUriString();

        return Image.builder()
                .path(fileDownloadUri)
                .producto(producto)
                .esPrincipal(esPrincipal)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public List<Image> subirImagenes(Long productoId, List<MultipartFile> files) {
        return IntStream.range(0, files.size()) .mapToObj(i -> subirImagen(productoId, files.get(i), i == 0)) .toList();
    }
}
