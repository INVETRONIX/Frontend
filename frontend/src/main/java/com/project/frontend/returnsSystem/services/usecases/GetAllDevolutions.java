package com.project.frontend.returnsSystem.services.usecases;

import java.util.List;

import com.project.frontend.returnsSystem.models.Devolution;
import com.project.frontend.returnsSystem.services.in.IGetAllDevolutions;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetAllDevolutions implements IGetAllDevolutions{

    private final String BASE_URL = "http://localhost:8080";
    private final IGetAllDevolutions service;

    public GetAllDevolutions() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IGetAllDevolutions.class);
    }

    @Override
    public Call<List<Devolution>> getAllDevolutions() {
        return service.getAllDevolutions();
    }
    
}