package com.project.frontend.IAsystem.services.in;

import retrofit2.Call;
import retrofit2.http.POST;

public interface IServiceIA {
    @POST("/prediccion")
    Call<String>prediccion();
}