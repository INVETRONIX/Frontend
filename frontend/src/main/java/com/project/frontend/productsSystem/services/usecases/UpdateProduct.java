package com.project.frontend.productsSystem.services.usecases;

import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.in.IUpdateProduct;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateProduct implements IUpdateProduct{
    private final String BASE_URL = "http://localhost:8080";
    private final IUpdateProduct service;

    public UpdateProduct() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IUpdateProduct.class);
    }


    public Call<Product> updateProduct(String id, Product product) {
        return service.updateProduct(id, product);
    }
}
