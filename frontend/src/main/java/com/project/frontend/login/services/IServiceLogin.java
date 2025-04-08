package com.project.frontend.login.services;

import com.project.frontend.registerUsers.models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IServiceLogin {
    
    @GET("/login")
    Call<User>login(@Query("correo") String correo,
                    @Query("password") String password
    );
}
