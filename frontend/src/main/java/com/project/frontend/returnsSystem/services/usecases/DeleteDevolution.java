package com.project.frontend.returnsSystem.services.usecases;

import com.project.frontend.returnsSystem.services.in.IDeleteDevolution;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteDevolution implements IDeleteDevolution {

    private final String BASE_URL = "http://localhost:8080";
    private final IDeleteDevolution service;

    public DeleteDevolution() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IDeleteDevolution.class);
    }

    @Override
    public Call<Void> deleteDevolution(String id) {
        return service.deleteDevolution(id);
    }

    
   

}