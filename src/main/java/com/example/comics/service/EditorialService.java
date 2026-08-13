package com.example.comics.service;

import com.example.comics.dtos.request.EditorialRequest;
import com.example.comics.dtos.response.EditorialResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EditorialService {
    // Lista de editoriales tanto para el cliente como para el administrador
    List<EditorialResponse> getAllEditorial();

    // Metodo para registrar una nueva editorial
    EditorialResponse storeEditorial(EditorialRequest request);
}
