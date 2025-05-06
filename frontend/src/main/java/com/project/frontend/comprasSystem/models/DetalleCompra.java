package com.project.frontend.comprasSystem.models;

import lombok.Data;

@Data
public class DetalleCompra {
    private Long id;
    private Producto producto;
    private Integer cantidad;
} 