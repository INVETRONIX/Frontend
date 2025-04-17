package com.project.frontend.login.services;

import com.project.frontend.login.services.in.IServiceLogin;
import com.project.frontend.registerUsers.models.Client;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLogin implements IServiceLogin{

    private final String Base_URL = "http://localhost:8080";
    private final IServiceLogin apiService;

    public ServiceLogin(){
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(Base_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        this.apiService = retrofit.create(IServiceLogin.class);
    }

    @Override
    public Call<Client> login(String email, String password) {
        return apiService.login(email, password);
    }
    
}