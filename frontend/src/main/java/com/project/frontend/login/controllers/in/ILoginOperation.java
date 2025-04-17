package com.project.frontend.login.controllers.in;

import java.io.IOException;
import com.project.frontend.registerUsers.models.Client;

public interface ILoginOperation {
    Client login(String email, String password) throws IOException;
}