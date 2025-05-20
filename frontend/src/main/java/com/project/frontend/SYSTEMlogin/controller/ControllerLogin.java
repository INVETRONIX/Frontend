package com.project.frontend.SYSTEMlogin.controller;

import java.io.IOException;

import com.project.frontend.SYSTEMlogin.data.TokenManager;
import com.project.frontend.SYSTEMlogin.model.LoginRequest;
import com.project.frontend.SYSTEMlogin.model.LoginResponse;
import com.project.frontend.SYSTEMlogin.service.ILoginService;
import com.project.frontend.core.BackendException;
import com.project.frontend.core.HandlerErrorResponse;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerLogin {
    private static final String BASE_URL = "http://localhost:8080";
    private ILoginService loginService;
    private HandlerErrorResponse handlerErrorResponse;

    public ControllerLogin(){
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        loginService = retrofit.create(ILoginService.class);
        handlerErrorResponse = new HandlerErrorResponse();
    }

    public LoginResponse login(LoginRequest loginRequest) throws BackendException {
        try {
            Response<LoginResponse> response = loginService.login(loginRequest).execute();
            if(response.isSuccessful()){
                LoginResponse loginResponse = response.body();
                if(loginResponse != null && loginResponse.getToken() != null){
                    //Serializo el token en Singleton
                    TokenManager.getInstance().saveToken(loginResponse.getToken());
                    return loginResponse;
                }
            }
            handlerErrorResponse.handleErrorResponse(response);
            return null;
        } catch (Exception e) {
            if (e instanceof BackendException) {
                throw (BackendException) e;
            }
            throw new BackendException(500, "Error al conectar con el servidor: " + e.getMessage());
        }
    }
    
}