package com.project.frontend.registerUsers.services;

import com.project.frontend.registerUsers.models.Client;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IServiceRegister {
    
    @POST("/api/register")
    Call<Client> save(@Body Client user);
}