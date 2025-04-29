package com.project.frontend.returnsSystem.services.in;


import com.project.frontend.returnsSystem.models.Devolution;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUpdateDevolution {
    @PUT("/api/devolution/{id}")
    Call<Devolution> updateDevolution(@Path("id") String id, @Body Devolution devolution);
}