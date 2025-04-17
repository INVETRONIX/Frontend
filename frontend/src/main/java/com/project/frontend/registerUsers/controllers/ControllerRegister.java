package com.project.frontend.registerUsers.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.frontend.registerUsers.controllers.components.PostOperation;
import com.project.frontend.registerUsers.controllers.in.IPostOperation;
import com.project.frontend.registerUsers.models.Client;

public class ControllerRegister {

    private final IPostOperation postOperation;

    @Autowired
    public ControllerRegister() {
        this.postOperation = new PostOperation();
    }
 
    public void operation(String query, Object[] body) throws IOException {
        switch (query) {
            case "POST":
                postOperation.save((Client) body[0]);
                break;
            default:
                break;
        }
    }

}