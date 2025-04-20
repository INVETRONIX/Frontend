package com.project.frontend.purchasesSystem.controllers.components;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.purchasesSystem.controllers.in.ISaveOperation;
import com.project.frontend.purchasesSystem.models.Purchase;
import com.project.frontend.purchasesSystem.services.ServicePurchases;
import com.project.frontend.shared.handlers.HandlerError;
import com.project.frontend.shared.handlers.HandlerMessage;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public class SaveOperation implements ISaveOperation{
    private final ServicePurchases servicePurchases;

    @Override
    public Purchase savePurchase(Purchase purchase) throws IOException {
       Call<Purchase> call = servicePurchases.operation("POST", purchase);
        Response<Purchase> response = call.execute();
        
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