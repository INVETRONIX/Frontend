package com.project.frontend.purchasesSystem.services.in;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface IDeletePurchase {
    @DELETE("/api/purchase/{id}")
    Call<Void> deletePurchase(@Path("id") String id);
}
