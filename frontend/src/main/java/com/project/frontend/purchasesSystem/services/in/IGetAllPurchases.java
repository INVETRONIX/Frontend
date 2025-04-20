package com.project.frontend.purchasesSystem.services.in;

import java.util.List;

import com.project.frontend.purchasesSystem.models.Purchase;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IGetAllPurchases {
    @GET("/api/purchase")
    Call<List<Purchase>> getAllPurchases();
}
