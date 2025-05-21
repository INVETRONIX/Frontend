package com.project.frontend.SYSTEMimages.controller;

import java.io.IOException;
import com.project.frontend.SYSTEMimages.service.IServiceImages;
import com.project.frontend.core.BackendException;
import com.project.frontend.core.HandlerErrorResponse;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerImages {
    private static final String BASE_URL= "http://localhost:8080";
    private final IServiceImages serviceImages;
    private final HandlerErrorResponse handlerErrorResponse;

    public ControllerImages(){
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        serviceImages = retrofit.create(IServiceImages.class);
        handlerErrorResponse = new HandlerErrorResponse();
    }

    public String getImage(String query) throws IOException, BackendException{
        Response<String> response = serviceImages.getImage(query).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }
}
