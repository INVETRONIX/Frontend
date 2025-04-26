package com.project.frontend.productsSystem.services.in;

import java.util.List;
import com.project.frontend.productsSystem.models.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFindProductsByFilters {
    @GET("/api/products/search")
    Call<List<Product>> findByFilters(
        @Query("nameProduct") String nameProduct,
        @Query("category") String category,
        @Query("nameSupplier")String nameSupplier    
    );
}