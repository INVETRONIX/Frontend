package com.project.frontend.SYSTEMlogin.data;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TokenManager {
    private static TokenManager instance;
    private static String token;
    private static String userRole;
    private static Long userId;

    private TokenManager(){}

    public static TokenManager getInstance(){
        if(instance == null){
            instance = new TokenManager();
        }
        return instance;
    }

    public void saveToken(String token){
        this.token = token;
        if (token != null && !token.isEmpty()) {
            decodeTokenInfo();
        } else {
            clearUserInfo();
        }
    }

    public String getToken(){
        return token;
    }

    public String getUserRole(){
        return userRole;
    }

    public Long getUserId() {
        return userId;
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
            
            // Ajustar el padding para Base64 si es necesario
            while (payload.length() % 4 != 0) {
                payload += "=";
            }
            
            // Decodificar de Base64
            byte[] decodedBytes;
            try {
                decodedBytes = Base64.getUrlDecoder().decode(payload);
            } catch (IllegalArgumentException e) {
                // Intenta con el decodificador estándar si el URL decoder falla
                decodedBytes = Base64.getDecoder().decode(payload);
            }
            
            String decodedPayload = new String(decodedBytes, StandardCharsets.UTF_8);
            System.out.println("Decoded payload: " + decodedPayload);

            // Parsear el JSON
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(decodedPayload, JsonObject.class);

            // Extraer la información
            if (jsonObject.has("rol")) {
                userRole = jsonObject.get("rol").getAsString();
            }

            // Extraer el userId del campo id
            if (jsonObject.has("id")) {
                try {
                    userId = jsonObject.get("id").getAsLong();
                    System.out.println("Extracted userId from id field: " + userId);
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear userId: " + e.getMessage());
                }
            } else {
                System.err.println("No se encontró el campo 'id' en el token");
            }

        } catch (Exception e) {
            System.err.println("Error al decodificar el token: " + e.getMessage());
            e.printStackTrace();
            clearUserInfo();
        }
    }

    private void clearUserInfo() {
        userRole = null;
        userId = null;
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