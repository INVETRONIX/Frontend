package com.project.frontend.login.services.in;

import com.project.frontend.registerUsers.models.Client;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IServiceLogin {
    
    @GET("/login")
    Call<Client> login(@Query("email")String email, @Query("password")String password);

}