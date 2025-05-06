package com.project.frontend.comprasSystem.models;

import lombok.Data;
import java.util.List;

@Data
public class Compra {
    private Long id;
    private Usuario usuario;
    private String fechaCompra;
    private String horaCompra;
    private List<DetalleCompra> detalles;
    private Double total;
} 