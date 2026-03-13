package com.example.comics.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileStorageProperties {
    private String url = "uploads/productos";
    private String maxFileSize = "5MB";
    private String maxRequestSize = "20MB";
}
