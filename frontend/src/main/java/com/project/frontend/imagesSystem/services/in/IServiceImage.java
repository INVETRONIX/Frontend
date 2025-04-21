package com.project.frontend.imagesSystem.services.in;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IServiceImage {
    
    @GET("/api/images")
    Call <String> searchImage(@Query("query") String query);
}
