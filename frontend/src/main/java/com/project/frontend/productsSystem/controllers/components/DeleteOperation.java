package com.project.frontend.productsSystem.controllers.components;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.project.frontend.productsSystem.controllers.in.IDeleteOperation;
import com.project.frontend.productsSystem.services.ServiceProducts;
import com.project.frontend.shared.handlers.HandlerError;
import com.project.frontend.shared.handlers.HandlerMessage;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public class DeleteOperation implements IDeleteOperation{
    private final ServiceProducts serviceProducts;

    public boolean deleteProduct(String id) throws IOException {
        Call<Void> call = serviceProducts.operation("DELETE", id);
        Response<Void> response = call.execute();
        if (response.isSuccessful()) {
            SwingUtilities.invokeLater(() -> 
                HandlerMessage.mostrarMensaje("Producto eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE));
            return true;
        } else {
            HandlerError.manejarErrorAsync(response);
            return false;
        }
    }
}