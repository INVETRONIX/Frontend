package com.project.frontend.purchasesSystem.controllers.components;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.purchasesSystem.controllers.in.IUpdateOperation;
import com.project.frontend.purchasesSystem.models.Purchase;
import com.project.frontend.purchasesSystem.services.ServicePurchases;
import com.project.frontend.shared.handlers.HandlerError;
import com.project.frontend.shared.handlers.HandlerMessage;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public class UpdateOperation implements IUpdateOperation{
    private final ServicePurchases servicePurchases;

    @Override
    public Purchase updatePurchase(String id, Purchase purchase) throws IOException {
       Call<Purchase> call = servicePurchases.operation("UPDATE", id, purchase);
        Response<Purchase> response = call.execute();
        
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
