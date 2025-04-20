package com.project.frontend.purchasesSystem.services.in;

import com.project.frontend.purchasesSystem.models.Purchase;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IGetPurchaseById {
    @GET("/api/purchases/{id}")
    Call<Purchase> getPurchaseById(@Path("id") String id);
}
