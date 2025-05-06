package com.project.frontend.comprasSystem.models;

import lombok.Data;

@Data
public class Producto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private double precio;
} 