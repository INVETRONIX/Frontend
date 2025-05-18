package com.project.frontend.SYSTEMcompras.model;

import java.time.LocalDate;
import java.time.LocalTime;
import com.google.gson.annotations.SerializedName;
import com.project.frontend.SYSTEMproductos.model.Producto;
import com.project.frontend.SYSTEMregistro.model.Usuario;

public class Compra {
    @SerializedName("id")
    private Long id;

    @SerializedName("fecha")
    private LocalDate fecha;

    @SerializedName("hora")
    private LocalTime hora;

    @SerializedName("usuario")
    private Usuario usuario;

    @SerializedName("producto")
    private Producto producto;

    @SerializedName("total")
    private Double total;

    public Compra(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", usuario=" + usuario +
                ", producto=" + producto +
                ", total=" + total +
                '}';
    }
}