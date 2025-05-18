package com.project.frontend.SYSTEMgemini.service;

import com.project.frontend.SYSTEMgemini.model.GeminiResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IGeminiService {
    @GET("/api/gemini/predecir")
    Call<GeminiResponse> analizarVentas();
}