package com.project.frontend.returnsSystem.services.in;

import com.project.frontend.returnsSystem.models.Devolution;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ISaveDevolution {
    @POST("/api/devolution")
    Call<Devolution> saveDevolution(@Body Devolution devolution);
}