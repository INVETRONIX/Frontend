package com.project.frontend.productsSystem.services.in;

import com.project.frontend.productsSystem.models.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IsaveProduct {
    @POST("/api/products")
    Call<Product> saveProduct(@Body Product user);
}