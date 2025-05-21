package com.project.frontend.SYSTEMimages.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Headers;

public interface IServiceImages {
    @Headers({
        "Accept: application/json",
        "Content-Type: application/json"
    })
    @GET("/api/images")
    Call<String> getImage(@Query("query") String query);
}