package com.project.frontend.productsSystem.controllers.components;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.project.frontend.productsSystem.controllers.in.ISaveOperation;
import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.ServiceProducts;
import com.project.frontend.shared.handlers.HandlerError;
import com.project.frontend.shared.handlers.HandlerMessage;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public class SaveOperation implements ISaveOperation {
    private final ServiceProducts serviceProducts;

    @Override
    public Product saveProduct(Product product) throws IOException {
        Call<Product> call = serviceProducts.operation("POST", product);
        Response<Product> response = call.execute();
        
        if (response.isSuccessful()) {
            SwingUtilities.invokeLater(() -> 
                HandlerMessage.mostrarMensaje("Producto guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE));
            return response.body();
        } else {
            HandlerError.manejarErrorAsync(response);
            return null;
        }
    }
}