package com.project.frontend.returnsSystem.services.in;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.project.frontend.returnsSystem.models.Devolution;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFindDevolutionsByFilters {
        @GET("/api/devolution/search")
    Call<List<Devolution>> findByFilters(
        @Query("nameClient") String nameClient,
        @Query("hour") String date,
        @Query("data") String hour    
    );
}