package com.project.frontend.productsSystem.services.in;

import java.util.List;
import com.project.frontend.productsSystem.models.Product;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IGetAllProducts {
    @GET("/api/products")
    Call<List<Product>> getAllProducts();
}
