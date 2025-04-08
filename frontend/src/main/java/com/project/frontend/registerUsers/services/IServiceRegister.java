package com.project.frontend.registerUsers.services;

import com.project.frontend.registerUsers.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IServiceRegister {
    
    @POST("/registro")
    Call<User> register(@Body User user);
}
