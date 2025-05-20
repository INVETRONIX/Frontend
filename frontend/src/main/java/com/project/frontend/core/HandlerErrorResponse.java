package com.project.frontend.core;

import retrofit2.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;

public class HandlerErrorResponse {
    private final Gson gson;

    public HandlerErrorResponse(){
        gson = new Gson();
    }

    public void handleErrorResponse(Response<?> response) throws BackendException {
        if(response.errorBody() != null){
            try{
                ResponseBody errorBody = response.errorBody();
                String errorString = errorBody.string();
                
                // Intentar parsear como JSON primero
                try {
                    JsonObject errorJson = gson.fromJson(errorString, JsonObject.class);
                    String mensaje = null;
                    
                    if (errorJson.has("message")) {
                        mensaje = errorJson.get("message").getAsString();
                    } else if (errorJson.has("error")) {
                        mensaje = errorJson.get("error").getAsString();
                    }
                    
                    if (mensaje != null && !mensaje.isEmpty()) {
                        throw new BackendException(response.code(), mensaje);
                    }
                } catch (Exception e) {
                    // Si no es JSON, usar el mensaje directamente
                    if (!errorString.isEmpty()) {
                        throw new BackendException(response.code(), errorString);
                    }
                }
                
                // Si no se pudo obtener un mensaje específico, usar el mensaje por defecto
                throw new BackendException(response.code(), getDefaultErrorMessage(response.code()));
            } catch(Exception e){
                if (e instanceof BackendException) {
                    throw (BackendException) e;
                }
                throw new BackendException(response.code(), "Error al procesar la respuesta del servidor: " + e.getMessage());
            }
        }
        throw new BackendException(response.code(), getDefaultErrorMessage(response.code()));
    }

    private String getDefaultErrorMessage(int statusCode) {
        switch (statusCode) {
            case 400:
                return "Solicitud inválida";
            case 401:
                return "No autorizado. Por favor inicie sesión nuevamente";
            case 403:
                return "Acceso denegado";
            case 404:
                return "Recurso no encontrado";
            case 409:
                return "Conflicto con el estado actual del recurso";
            case 500:
                return "Error interno del servidor";
            default:
                return "Error desconocido (Código: " + statusCode + ")";
        }
    }
}
