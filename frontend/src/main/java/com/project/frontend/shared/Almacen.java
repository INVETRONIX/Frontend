package com.project.frontend.shared;

import java.util.List;

import com.project.frontend.registroProductos.models.Producto;
import com.project.frontend.registroUsuarios.models.Administrador;

public class Almacen {
    private String nit;
    private List<Producto> productos;
    private String nombre;
    private String ubicacion;
    private Administrador administrador;

    public Almacen(String nit, List<Producto> productos, String nombre, String ubicacion, Administrador administrador) {
        this.nit = nit;
        this.productos = productos;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.administrador = administrador;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public String toString(){
        return "Amacen{" +
        "nit='" + nit + '\'' +
        ", productos='" + productos + '\'' +
        ", nombre='" + nombre + '\'' +
        ", ubicacion='" + ubicacion + '\'' +
            "Administrador{" +
            "name='" + administrador.getName() + '\'' +
            ", email='" + administrador.getEmail() + '\'' +
            ", password='" + administrador.getPassword() + '\'' +
            ", experiencia='" + administrador.getExperiencia() + '\'' +
            ", sueldo='" + administrador.getSueldo() +
            '}';
    }
}
