package com.project.frontend.registroUsuarios.services;

import com.project.frontend.registroUsuarios.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRegisterService {
    @POST("api/usuarios")
    Call<User> save(@Body User user);

    @GET("api/usuarios/{id}")
    Call<User> findById(@Path("id") String id);
}
