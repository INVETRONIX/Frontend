package com.project.frontend.returnsSystem.services.usecases;

import com.project.frontend.returnsSystem.models.Devolution;
import com.project.frontend.returnsSystem.services.in.IGetDevolutionById;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetDevolutionById implements IGetDevolutionById{

    private final String BASE_URL = "http://localhost:8080";
    private final IGetDevolutionById service;

    public GetDevolutionById() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IGetDevolutionById.class);
    }

    @Override
    public Call<Devolution> getDevolutionById(String id) {
        return service.getDevolutionById(id);
    }

    
    
}