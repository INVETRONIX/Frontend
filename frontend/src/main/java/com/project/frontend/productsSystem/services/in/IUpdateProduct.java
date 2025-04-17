package com.project.frontend.productsSystem.services.in;

import com.project.frontend.productsSystem.models.Product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUpdateProduct {
    @PUT("/api/products/{id}")
    Call<Product> updateProduct(@Path("id") String id, @Body Product product);
}