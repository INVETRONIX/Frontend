package com.project.frontend.productsSystem.services.usecases;

import java.util.List;
import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.in.IFindProductsByFilters;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindProductsByFilters implements IFindProductsByFilters {
    private final String BASE_URL = "http://localhost:8080";
    private final IFindProductsByFilters service;

    public FindProductsByFilters() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IFindProductsByFilters.class);
    }

    public Call<List<Product>> findByFilters(String nameProduct, String category, String nameSupplier) {
        return this.service.findByFilters(nameProduct, category, nameSupplier);
    }

}