package com.project.frontend.registerUsers.controllers.in;

import java.io.IOException;
import com.project.frontend.registerUsers.models.Client;

public interface IPostOperation {
    Client save(Client client) throws IOException;
}
