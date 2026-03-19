package com.example.comics.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
// Anotaciones Hibernate
@SQLDelete(sql = "UPDATE my_entity SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at is null")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "editorial", nullable = false)
    private String editorial;
    
    @Column(name = "autor")
    private String autor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoProducto tipo; 

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(nullable = false)
    private Boolean disponible = true;

    // --- Relacion con Imagen ---
    @OneToMany(
            mappedBy = "producto",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Image> images = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // ==========================================
    // MÉTODOS DE VALIDACIÓN
    // ==========================================
    public boolean esComic() {
        return tipo == TipoProducto.COMIC;
    }

    public boolean esManga(){
        return tipo == TipoProducto.MANGA;
    }

    public boolean esLibro(){
        return tipo == TipoProducto.LIBRO;
    }

    public boolean esOtro(){
        return tipo == TipoProducto.OTRO;
    }

    // ==========================================
    // MÉTODOS HELPER
    // ==========================================
    public void agregarImagen(Image imagen){
        images.add(imagen);
        imagen.setProducto(this);
    }

    public void removerImagen(Image imagen){
        images.remove(imagen);
        imagen.setProducto(null);
    }

    public Optional<Image> obtenerImagenPrincipal(){
        return images.stream().filter(Image::getEsPrincipal).findFirst();
    }

    public void establecerImagenPrincipal(Image nuevaPrincipal){
        images.stream().filter(Image::getEsPrincipal).forEach(image -> image.setEsPrincipal(false));
        nuevaPrincipal.setEsPrincipal(true);
    }
}