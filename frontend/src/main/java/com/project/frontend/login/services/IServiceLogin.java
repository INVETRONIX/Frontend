package com.project.frontend.login.services;

import com.project.frontend.registerUsers.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface IServiceLogin {
    
    @GET("/login")
    Call<User> login(@Query("email")String email, @Query("password") String password);
}
