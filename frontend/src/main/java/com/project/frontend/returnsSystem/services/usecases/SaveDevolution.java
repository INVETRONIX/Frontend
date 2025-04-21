package com.project.frontend.returnsSystem.services.usecases;

import com.project.frontend.returnsSystem.models.Devolution;
import com.project.frontend.returnsSystem.services.in.ISaveDevolution;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaveDevolution implements ISaveDevolution{

    private final String BASE_URL = "http://localhost:8080";
    private final ISaveDevolution service;

    public SaveDevolution() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(ISaveDevolution.class);
    }

    @Override
    public Call<Devolution> saveDevolution(Devolution devolution) {
        return service.saveDevolution(devolution);
    }
    
}