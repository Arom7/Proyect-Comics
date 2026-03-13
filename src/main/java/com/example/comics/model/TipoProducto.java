package com.example.comics.model;

public enum TipoProducto {
    COMIC("Cómic"), MANGA("Manga"), LIBRO("Libro"), OTRO("Otro");

    private final String nombre;

    TipoProducto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
