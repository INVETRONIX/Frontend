package com.project.frontend.purchasesSystem.services.usecases;

import com.project.frontend.purchasesSystem.models.Purchase;
import com.project.frontend.purchasesSystem.services.in.IGetPurchaseById;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetPurchaseById implements IGetPurchaseById{
    private final String BASE_URL = "http://localhost:8080";
    private final IGetPurchaseById service;

    public GetPurchaseById() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IGetPurchaseById.class);
    }

    @Override
    public Call<Purchase> getPurchaseById(String id) {
        return service.getPurchaseById(id);
    }
    
}
