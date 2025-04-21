package com.project.frontend.returnsSystem.controllers.components;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.project.frontend.returnsSystem.controllers.in.ISaveOperation;
import com.project.frontend.returnsSystem.models.Devolution;
import com.project.frontend.returnsSystem.services.ServiceDevolution;
import com.project.frontend.shared.handlers.HandlerError;
import com.project.frontend.shared.handlers.HandlerMessage;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public class SaveOperation implements ISaveOperation{
    private final ServiceDevolution serviceDevolution;

    @Override
    public Devolution saveDevolution(Devolution devolution) throws IOException {
        Call<Devolution> call = serviceDevolution.operation("POST", devolution);
        Response<Devolution> response = call.execute();
        
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