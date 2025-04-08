package com.project.frontend.registerUsers.controllers;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.google.gson.Gson;
import com.project.frontend.registerUsers.models.User;
import com.project.frontend.registerUsers.services.IServiceRegister;
import com.project.frontend.registerUsers.services.ServiceRegister;
import com.project.frontend.shared.ErrorResponse;

import retrofit2.Response;

public class ControllerRegister {
    private final IServiceRegister serviceRegister;
    private final Gson gson = new Gson();

    public ControllerRegister(){
        this.serviceRegister = new ServiceRegister();
    }

    public User register(User user) throws IOException{
        Response<User> response = serviceRegister.register(user).execute();
        if(response.isSuccessful()){
            SwingUtilities.invokeLater(() -> 
            mostrarMensaje("Bienvenido!", "Éxito", JOptionPane.INFORMATION_MESSAGE));
        }else{
            SwingUtilities.invokeLater(() -> {
                try {
                    manejarError(response);
                } catch (IOException e) {
                    mostrarMensaje("Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            return null;
        }
        return null;
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