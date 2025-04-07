package com.project.frontend.login.services;

import com.project.frontend.registerUsers.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ILoginService {
    @GET("/login")
    Call<User> login(@Query("correo") String correo, 
    @Query("contraseña") String contraseña);
}