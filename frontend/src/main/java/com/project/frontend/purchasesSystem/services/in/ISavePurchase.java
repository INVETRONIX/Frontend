package com.project.frontend.purchasesSystem.services.in;

import com.project.frontend.purchasesSystem.models.Purchase;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ISavePurchase {
    @POST("/api/purchases")
    Call<Purchase> savePurchase(@Body Purchase puchase);
}
