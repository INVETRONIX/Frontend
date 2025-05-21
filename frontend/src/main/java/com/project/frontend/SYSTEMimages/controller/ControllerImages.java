package com.project.frontend.SYSTEMimages.controller;

import java.io.IOException;
import com.project.frontend.SYSTEMimages.service.IServiceImages;
import com.project.frontend.core.BackendException;
import com.project.frontend.core.HandlerErrorResponse;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ControllerImages {
    private static final String BASE_URL= "http://localhost:8080";
    private final IServiceImages serviceImages;
    private final HandlerErrorResponse handlerErrorResponse;
    private final Gson gson;

    public ControllerImages(){
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
        .build();

        serviceImages = retrofit.create(IServiceImages.class);
        handlerErrorResponse = new HandlerErrorResponse();
        gson = new GsonBuilder().setLenient().create();
    }

    public String getImage(String query) throws IOException, BackendException {
        Response<String> response = serviceImages.getImage(query).execute();
        if(response.isSuccessful() && response.body() != null){
            try {
                System.out.println("Respuesta del servidor: " + response.body()); // Para depuración
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                
                if (jsonResponse.has("status") && jsonResponse.get("status").getAsString().equals("success")) {
                    return jsonResponse.get("imageUrl").getAsString();
                } else if (jsonResponse.has("message")) {
                    System.err.println("Error del servidor: " + jsonResponse.get("message").getAsString());
                }
            } catch (Exception e) {
                System.err.println("Error al procesar la respuesta: " + e.getMessage());
                e.printStackTrace();
            }
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }
}
