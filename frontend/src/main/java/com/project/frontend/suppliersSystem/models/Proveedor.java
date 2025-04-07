package com.project.frontend.suppliersSystem.models;

import com.project.frontend.shared.Almacen;

public class Proveedor {
    private String id;
    private String nombre;
    private Almacen almacen;
    private String telefono;
    private String email;

    public Proveedor(String id, String nombre, Almacen almacen, String telefono, String email ){
        this.id = id;
        this.nombre = nombre;
        this.almacen = almacen;
        this.telefono = telefono;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return "Proveedor{" +
        "id='" + id + '\'' +
        ", nombre='" + nombre + '\'' +
        "Almacen{" +
            "nit='" + almacen.getNit() + '\'' +
            ", productos='" + almacen.getProductos() + '\'' +
            ", nombre='" + almacen.getNombre() + '\'' +
            ", ubicacion='" + almacen.getUbicacion() + '\'' +
        "Administrador{" +
            "name='" + almacen.getAdministrador().getName() + '\'' +
            ", email='" + almacen.getAdministrador().getEmail() + '\'' +
            ", password='" + almacen.getAdministrador().getPassword() + '\'' +
            ", experiencia='" + almacen.getAdministrador().getExperiencia() + '\'' +
            ", sueldo='" + almacen.getAdministrador().getSueldo() + '\'' +
            '}';
    }
}
