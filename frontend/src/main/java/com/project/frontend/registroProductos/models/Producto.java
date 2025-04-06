package com.project.frontend.registroProductos.models;

import com.project.frontend.registroProveedores.models.Proveedor;

public class Producto {
    private String nombre;
    private String descripcion;
    private double precio;
    private String categoria;
    private int cantidadStock;
    private Proveedor proveedor;
    
    public Producto(String nombre, String descripcion, double precio, String categoria, int cantidadStock,
            Proveedor proveedor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.cantidadStock = cantidadStock;
        this.proveedor = proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public Proveedor GetProveedor(){
        return proveedor;
    }

    public void setIdProveedor(Proveedor proveedor){
        this.proveedor = proveedor;
    }

    public String toString(){
        return "Producto{" +
        "nombre='" + nombre + '\'' +
        ", descripcion='" + descripcion + '\'' +
        ", precio='" + precio + '\'' +
        ", categoria='" + categoria + '\'' +
        ", cantidadStock='" + cantidadStock + '\'' +
        "Proveedor{" +
            "id='" + proveedor.getId() + '\'' +
            ", nombre='" + proveedor.getNombre() + '\'' +
            "Almacen{" +
                "nit='" + proveedor.getAlmacen().getNit() + '\'' +
                ", productos='" + proveedor.getAlmacen().getProductos() + '\'' +
                ", nombre='" + proveedor.getAlmacen().getNombre() + '\'' +
                ", ubicacion='" + proveedor.getAlmacen().getUbicacion() + '\'' +
                "Administrador{" +
                    "name='" + proveedor.getAlmacen().getAdministrador().getName() + '\'' +
                    ", email='" + proveedor.getAlmacen().getAdministrador().getEmail() + '\'' +
                    ", password='" + proveedor.getAlmacen().getAdministrador().getPassword() + '\'' +
                    ", experiencia='" + proveedor.getAlmacen().getAdministrador().getExperiencia() + '\'' +
                    ", sueldo='" + proveedor.getAlmacen().getAdministrador().getSueldo() + '\'' +
        '}';
    }
}
