package com.project.frontend.productsSystem.controllers.components;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.project.frontend.productsSystem.controllers.in.IUpdateOperation;
import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.ServiceProducts;
import com.project.frontend.shared.handlers.HandlerError;
import com.project.frontend.shared.handlers.HandlerMessage;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public class UpdateOperation implements IUpdateOperation {
    private final ServiceProducts serviceProducts;

    @Override
    public Product updateProduct(String id, Product product) throws IOException {
        Call<Product> call = serviceProducts.operation("UPDATE", id, product);
        Response<Product> response = call.execute();
        
        if (response.isSuccessful()) {
            SwingUtilities.invokeLater(() -> 
                HandlerMessage.mostrarMensaje("Producto actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE));
            return response.body();
        } else {
            HandlerError.manejarErrorAsync(response);
            return null;
        }
    }
}
