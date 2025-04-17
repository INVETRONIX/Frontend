package com.project.frontend.shared.handlers;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.google.gson.Gson;
import com.project.frontend.shared.ErrorResponse;
import retrofit2.Response;

public class HandlerError {
    private final static Gson gson = new Gson();

    public static void manejarErrorAsync(Response<?> response) {
        SwingUtilities.invokeLater(() -> {
            try {
                manejarError(response);
            } catch (IOException e) {
                HandlerMessage.mostrarMensaje("Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void manejarError(Response<?> response) throws IOException {
        String errorBody = response.errorBody() != null ? response.errorBody().string() : "";
        if (!errorBody.isEmpty()) {
            try {
                ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                HandlerMessage.mostrarMensaje(errorResponse.getMessage(), "Error (" + response.code() + ")", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                HandlerMessage.mostrarMensaje("Error " + response.code() + ": " + errorBody, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            HandlerMessage.mostrarMensaje("Error " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
