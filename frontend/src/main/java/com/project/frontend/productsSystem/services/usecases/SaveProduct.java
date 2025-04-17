package com.project.frontend.productsSystem.services.usecases;

import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.in.IsaveProduct;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaveProduct implements IsaveProduct {
    private final String BASE_URL = "http://localhost:8080";
    private final IsaveProduct service;

    public SaveProduct() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IsaveProduct.class);
    }

  
    public Call<Product> saveProduct(Product user) {
        return service.saveProduct(user);
    }

}