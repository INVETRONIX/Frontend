package com.project.frontend.login.controllers;

import java.io.IOException;
import com.project.frontend.login.controllers.components.LoginOperation;
import com.project.frontend.login.controllers.in.ILoginOperation;
import com.project.frontend.registerUsers.models.Client;

public class ControllerLogin {

    private ILoginOperation loginOperation;

    public ControllerLogin() {
        this.loginOperation = new LoginOperation();
    }

    public Client operation(String email, String password) throws IOException {
        return loginOperation.login(email, password);
    }

}