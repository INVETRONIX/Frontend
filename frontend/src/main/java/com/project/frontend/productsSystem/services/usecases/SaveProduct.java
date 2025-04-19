package com.project.frontend.productsSystem.services.usecases;

import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.in.ISaveProduct;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaveProduct implements ISaveProduct {
    private final String BASE_URL = "http://localhost:8080";
    private final ISaveProduct service;

    public SaveProduct() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(ISaveProduct.class);
    }

  
    public Call<Product> saveProduct(Product user) {
        return service.saveProduct(user);
    }

}