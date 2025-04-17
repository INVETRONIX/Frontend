package com.project.frontend.productsSystem.services.in;

import com.project.frontend.productsSystem.models.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IGetProductById {
    @GET("/api/products/{id}")
    Call<Product> getProductById(@Path("id") String id);
}