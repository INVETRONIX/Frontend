package com.project.frontend.productsSystem.services.in;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface IDeleteProduct {
    @DELETE("/api/products/{id}")
    Call<Void> deleteProduct(@Path("id") String id);
}