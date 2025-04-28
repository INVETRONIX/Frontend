package com.project.frontend.IAsystem.controllers.components;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.project.frontend.IAsystem.controllers.in.IPredictionOperation;
import com.project.frontend.IAsystem.models.PredictionResponse;
import com.project.frontend.IAsystem.services.ServiceIA;
import com.project.frontend.IAsystem.services.in.IServiceIA;
import com.project.frontend.shared.handlers.HandlerError;
import com.project.frontend.shared.handlers.HandlerMessage;
import retrofit2.Response;

public class PredictionOperation implements IPredictionOperation{
    private final IServiceIA serviceIA;

    public PredictionOperation() {
        this.serviceIA = new ServiceIA();
    }

    @Override
    public String prediccion() throws IOException {
        Response<PredictionResponse> response = serviceIA.prediccion().execute();
        if(response.isSuccessful() && response.body() != null){
            return response.body().getPrediccion(); // Obtener el String de la respuesta JSON
        } else {
            SwingUtilities.invokeLater(() -> {
                try {
                    HandlerError.manejarError(response);
                } catch (IOException e) {
                    HandlerMessage.mostrarMensaje("Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            return null;
        }
    }
    
}