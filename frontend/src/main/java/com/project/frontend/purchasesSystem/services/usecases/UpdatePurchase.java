package com.project.frontend.purchasesSystem.services.usecases;

import com.project.frontend.purchasesSystem.models.Purchase;
import com.project.frontend.purchasesSystem.services.in.IUpdatePuerchase;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdatePurchase implements IUpdatePuerchase{
        private final String BASE_URL = "http://localhost:8080";
        private final IUpdatePuerchase service;

    public UpdatePurchase() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IUpdatePuerchase.class);
    }

    @Override
    public Call<Purchase> updatePurchase(String id, Purchase purchase) {
        return service.updatePurchase(id, purchase);
    }

}