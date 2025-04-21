package com.project.frontend.IAsystem.services;

import com.project.frontend.IAsystem.services.in.IServiceIA;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceIA implements IServiceIA{
    private final String Base_URL = "http://localhost:8080";
    private final IServiceIA apIServiceIA;

    public ServiceIA() {
         Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(Base_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        this.apIServiceIA = retrofit.create(IServiceIA.class);
    }

    @Override
    public Call<String> prediccion() {
        return apIServiceIA.prediccion();
    }
    
}
