package com.project.frontend.returnsSystem.services.usecases;

import com.project.frontend.returnsSystem.models.Devolution;
import com.project.frontend.returnsSystem.services.in.IUpdateDevolution;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateDevolution implements IUpdateDevolution{

    private final String BASE_URL = "http://localhost:8080";
    private final IUpdateDevolution service;

    public UpdateDevolution() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IUpdateDevolution.class);
    }

    @Override
    public Call<Devolution> updateDevolution(String id, Devolution devolution) {
        return service.updateDevolution(id, devolution);
    }
    
}