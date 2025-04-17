package com.project.frontend.productsSystem.services.usecases;

import com.project.frontend.productsSystem.services.in.IDeleteProduct;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteProduct implements IDeleteProduct {
    private final String BASE_URL = "http://localhost:8080";
    private final IDeleteProduct service;

    public DeleteProduct() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IDeleteProduct.class);
    }

    public Call<Void> deleteProduct(String id) {
        return service.deleteProduct(id);
    }
}