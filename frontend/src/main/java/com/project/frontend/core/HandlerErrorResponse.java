package com.project.frontend.core;

import java.io.IOException;
import retrofit2.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;

public class HandlerErrorResponse {
    private final Gson gson;

    public HandlerErrorResponse(){
        gson = new Gson();
    }

    public void handleErrorResponse(Response<?> response) throws IOException{
        if(response.errorBody() != null){
            try{
                ResponseBody errorBody = response.errorBody();
                String errorString = errorBody.string();
                JsonObject errorJson = gson.fromJson(errorString, JsonObject.class);
                String mensaje = errorJson.has("message") ? errorJson.get("message").getAsString() : "Error desconocido";
                throw new BackendException(response.code(), mensaje);
            } catch(Exception e){
                throw new IOException("Error al procesar la respuesta del servidor: " + e.getMessage());
            }
        }
        throw new IOException("Error en la respuesta del servidor: " + response.code());
    }
}
