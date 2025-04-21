package com.project.frontend.imagesSystem.controllers.components;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.project.frontend.imagesSystem.controllers.in.IImageOperation;
import com.project.frontend.imagesSystem.services.in.IServiceImage;
import com.project.frontend.shared.handlers.HandlerError;
import com.project.frontend.shared.handlers.HandlerMessage;

import retrofit2.Response;

public class ImageOperation implements IImageOperation{
    private IServiceImage serviceImage;

    @Override
    public String searchImage(String query) throws IOException{
        Response<String> response = serviceImage.searchImage(query).execute();
        if(response.isSuccessful() && response.body() != null){
            return response.body();
        }else{
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