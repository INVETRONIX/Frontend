package com.project.frontend.purchasesSystem.controllers.components;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.project.frontend.purchasesSystem.controllers.in.IDeleteOperation;
import com.project.frontend.purchasesSystem.services.ServicePurchases;
import com.project.frontend.shared.handlers.HandlerError;
import com.project.frontend.shared.handlers.HandlerMessage;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public class DeleteOperation implements IDeleteOperation {
    private final ServicePurchases servicePurchases;

    @Override
    public boolean deletePurchase(String id) throws IOException {
        Call<Void> call = servicePurchases.operation("DELETE", id);
        Response<Void> response = call.execute();
        if (response.isSuccessful()) {
            SwingUtilities.invokeLater(() -> 
                HandlerMessage.mostrarMensaje("compra eliminada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE));
            return true;
        } else {
            HandlerError.manejarErrorAsync(response);
            return false;
        }
    }

}