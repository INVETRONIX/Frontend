package com.project.frontend.productsSystem.services.usecases;

import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.in.IGetProductById;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetProductById implements IGetProductById {
    private final String BASE_URL = "http://localhost:8080";
    private final IGetProductById service;

    public GetProductById() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IGetProductById.class);
    }

   
    public Call<Product> getProductById(String id) {
        return service.getProductById(id);
    }

}