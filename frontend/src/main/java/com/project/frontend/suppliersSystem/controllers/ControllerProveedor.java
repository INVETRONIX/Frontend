package com.project.frontend.suppliersSystem.controllers;

import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.google.gson.Gson;
import com.project.frontend.shared.Almacen;
import com.project.frontend.shared.ErrorResponse;
import com.project.frontend.suppliersSystem.models.Proveedor;
import com.project.frontend.suppliersSystem.services.IProveedoresService;
import com.project.frontend.suppliersSystem.services.ProveedoresService;

import retrofit2.Response;

public class ControllerProveedor {
    private final IProveedoresService proveedorService;
    private final Gson gson = new Gson();

    public ControllerProveedor(){
        this.proveedorService = new ProveedoresService();
    }

    public void save(Proveedor proveedor){
        new Thread(() -> {
            try {
                Response<Proveedor> response = proveedorService.save(proveedor).execute();
                if (response.isSuccessful()) {
                    mostrarMensaje("Proveedor registrado exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
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
                Response<Proveedor> response = proveedorService.findById(id).execute();
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
                Response<Void> response = proveedorService.deleteProveedor(id).execute();
                if(response.isSuccessful()){
                    mostrarMensaje("Eliminado", "Exitoso", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                mostrarMensaje("NO se a podido eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    public void updateProveedor(String id, Proveedor proveedor){
        new Thread(() -> {
            try {
                Response<Proveedor> response = proveedorService.updateProveedor(id, proveedor).execute();
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
                Response<List<Proveedor>> response = proveedorService.findAll().execute();
                if(response.isSuccessful()) {
                    mostrarMensaje("Encontrado" + response.body(), "Exito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                mostrarMensaje("NO se han podido obtener los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    public void buscarProveedores(String id, String nombre, Almacen almacen, String telefono, String email){
        new Thread(() -> {
            try {
                Response<List<Proveedor>> response = proveedorService.buscarProveedores(id, nombre, almacen, telefono, email).execute();
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
