package com.example.comics.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "images", indexes = {
        @Index(name = "idx_producto_id" , columnList = "producto_id"),
        @Index(name = "idx_principal", columnList = "es_principal")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ruta de almacenamiento
    @Column(name = "path", nullable = false, length = 500)
    private String path;

    @Column(name = "nombre_archivo", length = 255)
    private String nombreArchivo;

    @Column(name = "es_principal", nullable = false)
    private Boolean esPrincipal = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Producto producto;

    @CreationTimestamp
    @Column(name = "created_at" , updatable = false)
    private LocalDateTime createdAt;
}
