package com.project.frontend.SYSTEMgemini.controller;

import java.io.IOException;
import com.project.frontend.SYSTEMgemini.model.GeminiResponse;
import com.project.frontend.SYSTEMgemini.service.IGeminiService;
import com.project.frontend.core.BackendException;
import com.project.frontend.core.HandlerErrorResponse;
import com.project.frontend.core.interceptorToken.Auth;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerGemini {
    private static final String BASE_URL = "http://localhost:8080";
    private final IGeminiService geminiService;
    private final HandlerErrorResponse handlerErrorResponse;

    public ControllerGemini(){ 

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Auth())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        geminiService = retrofit.create(IGeminiService.class);
        handlerErrorResponse = new HandlerErrorResponse();
    }

    public GeminiResponse analizarVentas() throws IOException, BackendException{
        Response<GeminiResponse> response = geminiService.analizarVentas().execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }
}