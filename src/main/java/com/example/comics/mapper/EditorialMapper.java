package com.example.comics.mapper;

import com.example.comics.dtos.request.EditorialRequest;
import com.example.comics.dtos.response.EditorialResponse;
import com.example.comics.model.Editorial;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface EditorialMapper {
    // Mapping de editorial a response
    EditorialResponse editorialToResponse(Editorial editorial);
    // Mapping de request a editorial
    Editorial requestToEditorial(EditorialRequest editorialRequest);
}
