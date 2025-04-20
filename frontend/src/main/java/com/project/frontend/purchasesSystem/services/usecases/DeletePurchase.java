package com.project.frontend.purchasesSystem.services.usecases;

import com.project.frontend.purchasesSystem.services.in.IDeletePurchase;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DeletePurchase implements IDeletePurchase{
    private final String BASE_URL = "http://localhost:8080";
    private final IDeletePurchase service;

    public DeletePurchase() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IDeletePurchase.class);
    }

    @Override
    public Call<Void> deletePurchase(String id) {
        return service.deletePurchase(id);
    }
}

