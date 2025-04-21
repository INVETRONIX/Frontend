package com.project.frontend.IAsystem.controllers;

import java.io.IOException;
import com.project.frontend.IAsystem.controllers.components.PredictionOperation;
import com.project.frontend.IAsystem.controllers.in.IPredictionOperation;

public class ControllerIA {
    private IPredictionOperation predictionOperation;

    public ControllerIA() {
        this.predictionOperation = new PredictionOperation();
    }

    public String operation() throws IOException{
        return predictionOperation.prediccion();
    }
    
}