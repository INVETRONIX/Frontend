package com.project.frontend.SYSTEMlogin.data;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TokenManager {
    private static TokenManager instance;
    private static String token;
    private static String userRole;

    private TokenManager(){}

    public static TokenManager getInstance(){
        if(instance == null){
            instance = new TokenManager();
        }
        return instance;
    }

    public void saveToken(String token){
        this.token = token;
        decodeTokenInfo();
    }

    public String getToken(){
        return token;
    }

    public String getUserRole(){
        return userRole;
    }

    private void decodeTokenInfo() {
        if (token == null || token.isEmpty()) {
            clearUserInfo();
            return;
        }

        try {
            // El JWT tiene 3 partes separadas por puntos: header.payload.signature
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Token JWT inválido");
            }

            // Decodificar el payload (segunda parte)
            String payload = parts[1];
            
            // Agregar padding si es necesario para Base64
            int paddingLength = (4 - payload.length() % 4) % 4;
            payload += "=".repeat(paddingLength);
            
            // Decodificar de Base64
            byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
            String decodedPayload = new String(decodedBytes, StandardCharsets.UTF_8);

            // Parsear el JSON
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(decodedPayload, JsonObject.class);

            // Extraer la información
            if (jsonObject.has("rol")) {
                userRole = jsonObject.get("rol").getAsString();
            }

        } catch (Exception e) {
            System.err.println("Error al decodificar el token: " + e.getMessage());
            clearUserInfo();
        }
    }

    private void clearUserInfo() {
        userRole = null;
    }

    public void clearToken() {
        token = null;
        clearUserInfo();
    }

    public boolean isLoggedIn() {
        return token != null && !token.isEmpty();
    }

    public boolean hasRole(String role) {
        return userRole != null && userRole.equals(role);
    
    }
}