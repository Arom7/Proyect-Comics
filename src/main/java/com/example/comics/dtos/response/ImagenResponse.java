package com.example.comics.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImagenResponse {
    private String path;
    private Boolean esPrincipal;
}
