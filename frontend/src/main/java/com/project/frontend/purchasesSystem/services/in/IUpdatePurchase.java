package com.project.frontend.purchasesSystem.services.in;

import com.project.frontend.purchasesSystem.models.Purchase;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUpdatePurchase {
    @PUT("/api/purchases/{id}")
    Call<Purchase> updatePurchase(@Path("id") String id, @Body Purchase purchase);
}
