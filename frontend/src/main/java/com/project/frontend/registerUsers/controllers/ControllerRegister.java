package com.project.frontend.registerUsers.controllers;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.google.gson.Gson;
import com.project.frontend.registerUsers.models.User;
import com.project.frontend.registerUsers.services.IRegisterService;
import com.project.frontend.registerUsers.services.RegisterService;
import com.project.frontend.shared.ErrorResponse;
import retrofit2.Response;

public class ControllerRegister {
    private final IRegisterService registerService;
    private final Gson gson = new Gson();

    public ControllerRegister() {
        this.registerService= new RegisterService();
    }

    public void save(User user) {
        new Thread(() -> {
            try {
                Response<User> response = registerService.save(user).execute();
                if (response.isSuccessful()) {
                    mostrarMensaje("Registro exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    manejarError(response);
                }
            } catch (IOException e) {
                mostrarMensaje("Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    public void findById(String id) {
        new Thread(() -> {
            try {
                Response<User> response = registerService.findById(id).execute();
                if (response.isSuccessful()) {
                    User cliente = response.body();
                    if (cliente != null) {
                        mostrarMensaje("Cliente encontrado: " + cliente.getName(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        mostrarMensaje("El cliente no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    manejarError(response);
                }
            } catch (IOException e) {
                mostrarMensaje("Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
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
