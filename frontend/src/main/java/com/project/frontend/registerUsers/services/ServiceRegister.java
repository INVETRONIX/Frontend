package com.project.frontend.registerUsers.services;

import com.project.frontend.registerUsers.models.Client;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRegister implements IServiceRegister{

    private final String Base_URL = "http://localhost:8080";
    private final IServiceRegister apiService;

    public ServiceRegister(){
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(Base_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        this.apiService = retrofit.create(IServiceRegister.class);
    }

    @Override
    public Call<Client> save(Client user) {
        return apiService.save(user);
    }
    
}