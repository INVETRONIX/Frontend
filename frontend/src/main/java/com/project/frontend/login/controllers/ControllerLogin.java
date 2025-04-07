package com.project.frontend.login.controllers;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.gson.Gson;
import com.project.frontend.login.services.ILoginService;
import com.project.frontend.login.services.LoginService;
import com.project.frontend.registerUsers.models.User;
import com.project.frontend.shared.ErrorResponse;
import retrofit2.Response;

public class ControllerLogin {
    private final ILoginService loginService;
    private final Gson gson = new Gson();

    @Autowired
    public ControllerLogin(){
        this.loginService=new LoginService();                
    }

    public User login(String email, String password) throws IOException {
        Response<User> response = loginService.login(email, password).execute();
        if (response.isSuccessful() && response.body() != null) {
            SwingUtilities.invokeLater(() -> 
            mostrarMensaje("Bienvenido!", "Éxito", JOptionPane.INFORMATION_MESSAGE));
            return response.body();
        } else {
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
