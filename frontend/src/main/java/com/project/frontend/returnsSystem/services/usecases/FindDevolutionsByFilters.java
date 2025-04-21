package com.project.frontend.returnsSystem.services.usecases;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.project.frontend.returnsSystem.models.Devolution;
import com.project.frontend.returnsSystem.services.in.IFindDevolutionsByFilters;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindDevolutionsByFilters implements IFindDevolutionsByFilters {

    private final String BASE_URL = "http://localhost:8080";
    private final IFindDevolutionsByFilters service;

    public FindDevolutionsByFilters() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IFindDevolutionsByFilters.class);
    }

    @Override
    public Call<List<Devolution>> findByFilters(String nameClient, LocalDate date, LocalTime hour) {
        return this.service.findByFilters(nameClient, date, hour);
    }

}