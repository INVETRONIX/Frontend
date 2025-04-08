package com.project.frontend.productsSystem.controllers;

import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.gson.Gson;
import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.IServiceProducts;
import com.project.frontend.productsSystem.services.ServiceProducts;
import com.project.frontend.shared.ErrorResponse;
import retrofit2.Response;

public class ControllerProduct {
    private final IServiceProducts serviceProducts;
    private final Gson gson = new Gson();

    @Autowired
    public ControllerProduct(){
        this.serviceProducts= new ServiceProducts();
    }

    public Product saveProduct(Product product) throws IOException {
        Response<Product> response = serviceProducts.saveProduct(product).execute();
        if (response.isSuccessful() && response.body() != null) {
            SwingUtilities.invokeLater(() -> 
                mostrarMensaje("Producto guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE));
            return response.body();
        } else {
            manejarErrorAsync(response);
            return null;
        }
    }

    public Product getProductById(String id) throws IOException {
        Response<Product> response = serviceProducts.getProductById(id).execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body();
        } else {
            manejarErrorAsync(response);
            return null;
        }
    }

    public List<Product> getAllProducts() throws IOException {
        Response<List<Product>> response = serviceProducts.getAllProducts().execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body();
        } else {
            manejarErrorAsync(response);
            return null;
        }
    }

    public Product updateProduct(String id, Product product) throws IOException {
        Response<Product> response = serviceProducts.updateProduct(id, product).execute();
        if (response.isSuccessful() && response.body() != null) {
            SwingUtilities.invokeLater(() -> 
                mostrarMensaje("Producto actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE));
            return response.body();
        } else {
            manejarErrorAsync(response);
            return null;
        }
    }

    public boolean deleteProduct(String id) throws IOException {
        Response<Void> response = serviceProducts.deleteProduct(id).execute();
        if (response.isSuccessful()) {
            SwingUtilities.invokeLater(() -> 
                mostrarMensaje("Producto eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE));
            return true;
        } else {
            manejarErrorAsync(response);
            return false;
        }
    }

    public List<Product> findByFilters(String nameProduct, String category, String nameSupplier) throws IOException {
        Response<List<Product>> response = serviceProducts.findByFilters(nameProduct, category, nameSupplier).execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body();
        } else {
            manejarErrorAsync(response);
            return null;
        }
    }

    private void manejarErrorAsync(Response<?> response) {
        SwingUtilities.invokeLater(() -> {
            try {
                manejarError(response);
            } catch (IOException e) {
                mostrarMensaje("Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void manejarError(Response<?> response) throws IOException {
        String errorBody = response.errorBody() != null ? response.errorBody().string() : "";
        if (!errorBody.isEmpty()) {
            try {
                ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                mostrarMensaje(errorResponse.getMessage(), "Error (" + response.code() + ")", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                mostrarMensaje("Error " + response.code() + ": " + errorBody, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            mostrarMensaje("Error " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, tipo);
    }


}
