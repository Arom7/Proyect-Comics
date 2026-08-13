package com.example.comics.service.implementation;

import com.example.comics.dtos.request.EditorialRequest;
import com.example.comics.dtos.response.EditorialResponse;
import com.example.comics.mapper.EditorialMapper;
import com.example.comics.model.Editorial;
import com.example.comics.repository.EditorialRepository;
import com.example.comics.service.EditorialService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EditorialServiceImpl implements EditorialService {
    private final EditorialRepository editorialRepository;
    private final EditorialMapper editorialMapper;

    @Override
    public List<EditorialResponse> getAllEditorial(){
        return editorialRepository.findAll().stream()
                .map(editorialMapper::editorialToResponse).toList();
    }

    @Override
    public EditorialResponse storeEditorial(EditorialRequest request){
        Editorial editorial = editorialMapper.requestToEditorial(request);
        return editorialMapper.editorialToResponse(editorialRepository.save(editorial));
    }
}
