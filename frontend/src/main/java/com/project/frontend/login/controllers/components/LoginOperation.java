package com.project.frontend.login.controllers.components;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.project.frontend.login.controllers.in.ILoginOperation;
import com.project.frontend.login.services.ServiceLogin;
import com.project.frontend.login.services.in.IServiceLogin;
import com.project.frontend.registerUsers.models.Client;
import com.project.frontend.shared.handlers.HandlerError;
import com.project.frontend.shared.handlers.HandlerMessage;
import retrofit2.Response;

public class LoginOperation implements ILoginOperation{
    private final IServiceLogin serviceLogin;

    public LoginOperation() {
        this.serviceLogin = new ServiceLogin();
    }

    public Client login(String email, String password) throws IOException{
        Response<Client> response = serviceLogin.login(email, password).execute();
        if(response.isSuccessful() && response.body() != null){
            SwingUtilities.invokeLater(() -> 
            HandlerMessage.mostrarMensaje("Bienvenido!", "Éxito", JOptionPane.INFORMATION_MESSAGE));
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