package com.project.frontend.returnsSystem.services.in;

import com.project.frontend.returnsSystem.models.Devolution;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IGetDevolutionById {
    @GET("/api/devolutions/{id}")
    Call<Devolution> getDevolutionById(@Path("id") String id);
}