package com.project.frontend.core.tokenManager;

import java.io.IOException;

import com.project.frontend.SYSTEMlogin.data.TokenManager;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Auth implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        
        // Si no hay token, continuar con la petición original
        String token = TokenManager.getInstance().getToken();
        if (token == null || token.isEmpty()) {
            return chain.proceed(originalRequest);
        }
        
        // Agregar el token al header Authorization
        Request authenticatedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();
        
        return chain.proceed(authenticatedRequest);
    }
    
}