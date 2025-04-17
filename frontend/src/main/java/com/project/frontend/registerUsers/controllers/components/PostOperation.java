package com.project.frontend.registerUsers.controllers.components;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.project.frontend.registerUsers.controllers.in.IPostOperation;
import com.project.frontend.registerUsers.models.Client;
import com.project.frontend.registerUsers.services.IServiceRegister;
import com.project.frontend.registerUsers.services.ServiceRegister;
import com.project.frontend.shared.handlers.HandlerError;
import com.project.frontend.shared.handlers.HandlerMessage;
import retrofit2.Response;

public class PostOperation implements IPostOperation{
    private final IServiceRegister serviceRegister;

    public PostOperation() {
        this.serviceRegister = new ServiceRegister();
        
    }
 
    public Client save(Client client) throws IOException {
        Response<Client> response = serviceRegister.save(client).execute();
        if (response.isSuccessful() && response.body() != null) {
            SwingUtilities.invokeLater(()->
                HandlerMessage.mostrarMensaje("Registrado correctamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE)
            );
            return response.body();
        }else{
            HandlerError.manejarErrorAsync(response);
            return null;
        }
    }

}