package com.project.frontend.comprasSystem.models;

import lombok.Data;
import java.io.Serializable;

@Data
public class ItemCarrito implements Serializable {
    private Producto producto;
    private int cantidad;

    public ItemCarrito() {
        this.cantidad = 1;
    }

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
} 