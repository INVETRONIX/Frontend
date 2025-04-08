package com.project.frontend.login.controllers;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.google.gson.Gson;
import com.project.frontend.login.services.IServiceLogin;
import com.project.frontend.login.services.ServiceLogin;
import com.project.frontend.registerUsers.models.User;
import com.project.frontend.shared.ErrorResponse;
import retrofit2.Response;



public class ControllerLogin {
    private final IServiceLogin serviceLogin;
    private final Gson gson = new Gson();

    public ControllerLogin(){
        this.serviceLogin = new ServiceLogin();
    }

    public User login(String email, String password) throws IOException{
        Response<User> response = serviceLogin.login(email, password).execute();
        if(response.isSuccessful() && response.body() != null){
            SwingUtilities.invokeLater(() -> 
            mostrarMensaje("Bienvenido!", "Éxito", JOptionPane.INFORMATION_MESSAGE));
            return response.body();
        }else{
            SwingUtilities.invokeLater(() -> {
                try {
                    manejarError(response);
                } catch (IOException e) {
                    mostrarMensaje("Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            return null;
        }
    }
    private void manejarError(Response<?> response) throws IOException {
        String errorBody = response.errorBody() != null ? response.errorBody().string() : "";
        if (!errorBody.isEmpty()) {
            try {
                ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                mostrarMensaje(errorResponse.getMessage(), "Error (" + response.code() + ")", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                mostrarMensaje("Error " + response.code() + ": " + errorBody, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            mostrarMensaje("Error " + response.code(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, mensaje, titulo, tipo));
    }
}
