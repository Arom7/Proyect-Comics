package com.example.comics.controller;

import com.example.comics.dtos.request.EditorialRequest;
import com.example.comics.dtos.response.ApiResponse;
import com.example.comics.dtos.response.EditorialResponse;
import com.example.comics.service.EditorialService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editorial")
@AllArgsConstructor
@Slf4j

public class EditorialController {
    private final EditorialService editorialService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EditorialResponse>>> getAllEditorial(){
        List<EditorialResponse> response = editorialService.getAllEditorial();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EditorialResponse>> store(
            @RequestBody @Valid EditorialRequest editorial
    ){
        EditorialResponse response = editorialService.storeEditorial(editorial);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(ApiResponse.success(response));
    }
}
