package com.project.frontend.productsSystem.services;

import java.util.List;
import com.project.frontend.productsSystem.models.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IServiceProducts {

    @POST("/api/products")
    Call<Product> saveProduct(@Body Product user);

    @GET("/api/products/{id}")
    Call<Product> getProductById(@Path("id") String id);

    @GET("/api/products")
    Call<List<Product>> getAllProducts();

    @PUT("/api/products/{id}")
    Call<Product> updateProduct(@Path("id") String id, @Body Product product);

    @DELETE("/api/products/{id}")
    Call<Void> deleteProduct(@Path("id") String id);

    @GET("/api/products/buscar")
    Call<List<Product>> findByFilters(
        @Query("nameProduct") String nameProduct,
        @Query("category") String category,
        @Query("nameSupplier")String nameSupplier    
    );
}
