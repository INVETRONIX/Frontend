package com.project.frontend.comprasSystem.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrito implements Serializable {
    private List<ItemCarrito> items;
    private double total;

    public Carrito() {
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addItem(ItemCarrito item) {
        items.add(item);
        total += item.getProducto().getPrecio() * item.getCantidad();
    }

    public void removeItem(Long productoId) {
        items.removeIf(item -> item.getProducto().getId().equals(productoId));
        calcularTotal();
    }

    public void vaciar() {
        items.clear();
        total = 0.0;
    }

    private void calcularTotal() {
        total = items.stream()
            .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
            .sum();
    }
} 