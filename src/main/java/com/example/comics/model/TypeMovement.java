package com.example.comics.model;

public enum TypeMovement {
    ENTRADA("Entrada"), SALIDA("Salida"), AJUSTE("Ajuste"), DEVOLUCION("Devolucion");

    private final String movement;

    TypeMovement(String movement) { this.movement = movement; }

    public String getMovement() { return movement; }
}
