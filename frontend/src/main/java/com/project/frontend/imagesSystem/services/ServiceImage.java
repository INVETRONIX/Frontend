package com.project.frontend.imagesSystem.services;

import com.project.frontend.imagesSystem.services.in.IServiceImage;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceImage implements IServiceImage {

    private final String Base_URL = "http://localhost:8080";
    private final IServiceImage apIServiceImage;

    public ServiceImage() {
         Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(Base_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        this.apIServiceImage = retrofit.create(IServiceImage.class);
    }

    @Override
    public Call<String> searchImage(String query) {
        return apIServiceImage.searchImage(query);
    }

}