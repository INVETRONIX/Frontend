package com.project.frontend.returnsSystem.services.in;

import java.util.List;

import com.project.frontend.returnsSystem.models.Devolution;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IGetAllDevolutions {
    @GET("/api/devolution")
    Call<List<Devolution>> getAllDevolutions();
}