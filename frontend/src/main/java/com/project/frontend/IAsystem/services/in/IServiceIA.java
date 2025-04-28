package com.project.frontend.IAsystem.services.in;

import com.project.frontend.IAsystem.models.PredictionResponse;

import retrofit2.Call;
import retrofit2.http.POST;

public interface IServiceIA {
    @POST("/api/predicciones")
    Call<PredictionResponse> prediccion(); // Espera un objeto PredictionResponse
}