package com.project.frontend.imagesSystem.controllers;

import java.io.IOException;

import com.project.frontend.imagesSystem.controllers.components.ImageOperation;
import com.project.frontend.imagesSystem.controllers.in.IImageOperation;

public class ControllerImage {
    private final IImageOperation imageOperation;

    public ControllerImage(){
        this.imageOperation = new ImageOperation();
    }

    public String operation(String query) throws IOException{
        return imageOperation.searchImage(query);
    }
}
