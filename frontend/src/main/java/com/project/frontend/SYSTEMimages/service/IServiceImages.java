package com.project.frontend.SYSTEMimages.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IServiceImages {
    @GET("/api/images")
    Call<String> getImage(@Query("query") String query);
}