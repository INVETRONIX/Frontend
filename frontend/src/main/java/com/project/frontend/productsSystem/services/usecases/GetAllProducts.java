package com.project.frontend.productsSystem.services.usecases;

import java.util.List;
import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.in.IGetAllProducts;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetAllProducts implements IGetAllProducts {
    private final String BASE_URL = "http://localhost:8080";
    private final IGetAllProducts service;

    public GetAllProducts() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IGetAllProducts.class);
    }

  
    public Call<List<Product>> getAllProducts() {
        return service.getAllProducts();
    }

}