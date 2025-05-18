package com.project.frontend.SYSTEMlogin.service;

import com.project.frontend.SYSTEMlogin.model.LoginRequest;
import com.project.frontend.SYSTEMlogin.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ILoginService {
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
