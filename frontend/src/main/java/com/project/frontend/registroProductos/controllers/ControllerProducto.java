package com.project.frontend.registroProductos.controllers;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.google.gson.Gson;
import com.project.frontend.registroProductos.models.Producto;
import com.project.frontend.registroProductos.services.IProductosService;
import com.project.frontend.registroProductos.services.ProductosService;
import com.project.frontend.registroProveedores.models.Proveedor;
import com.project.frontend.shared.ErrorResponse;

import retrofit2.Response;



public class ControllerProducto {
    private final IProductosService productosService;
    private final Gson gson = new Gson();

    public ControllerProducto(){
        this.productosService = new ProductosService();
    }

    public void saved(Producto producto){
        new Thread(() -> {
            try {
                Response<Producto> response = productosService.save(producto).execute();
                if (response.isSuccessful()) {
                    mostrarMensaje("Registro exitoso", "Exito", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    manejarError(response);
                }
            } catch (Exception e) {
                mostrarMensaje("Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    
    public void findById(String id){
        new Thread(() -> {
            try {
                Response<Producto> response = productosService.findById(id).execute();
                if(response.isSuccessful()){
                    mostrarMensaje("Exito" + response.body(), "Encontrado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                mostrarMensaje("NO se a podido encontrar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    public void deleteProducto(String id){
        new Thread(() -> {
            try {
                Response<Void> response = productosService.deleteProducto(id).execute();
                if(response.isSuccessful()){
                    mostrarMensaje("Eliminado", "Exitoso", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                mostrarMensaje("NO se a podido eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    public void updateProducto(String id, Producto producto){
        new Thread(() -> {
            try {
                Response<Producto> response = productosService.updateProducto(id, producto).execute();
                if (response.isSuccessful()) {
                    mostrarMensaje("Modificación exitosa", "Exito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                mostrarMensaje("No se puso modificar" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    public void findAll() {
        new Thread(() -> {
            try {
                Response<List<Producto>> response = productosService.findAll().execute();
                if(response.isSuccessful()) {
                    mostrarMensaje("Encontrado" + response.body(), "Exito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                mostrarMensaje("NO se han podido obtener los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    public void buscarProductos(String nombre, String descripcion, double precio, String categoria, int cantidadStock, Proveedor proveedor){
        new Thread(() -> {
            try {
                Response<List<Producto>> response = productosService.buscarProductos(nombre, descripcion, precio, categoria, cantidadStock, proveedor).execute();
                if (response.isSuccessful()) {
                    mostrarMensaje("Encontrado" + response.body(), "Exito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                mostrarMensaje("Nos se han podido obtener los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    public void manejarError(Response<?> response) throws IOException{
        String errorBody = response.errorBody() != null ? response.errorBody().string() : "";
        if (errorBody.isEmpty()) {
            try{
                ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                mostrarMensaje(errorResponse.getMessage(), "Error (" + response.code() + ")", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e){
                mostrarMensaje("Error " + response.code() + ":" + errorBody,"Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            mostrarMensaje("Error " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        public void mostrarMensaje(String mensaje, String titulo, int tipo){
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, mensaje, titulo, tipo));
        }
}


