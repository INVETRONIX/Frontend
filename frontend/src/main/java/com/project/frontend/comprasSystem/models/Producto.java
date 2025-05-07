package com.project.frontend.comprasSystem.models;

import lombok.Data;
import java.io.Serializable;

@Data
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private double precio;
} 