package com.project.frontend.purchasesSystem.services.usecases;


import com.project.frontend.purchasesSystem.models.Purchase;
import com.project.frontend.purchasesSystem.services.in.ISavePurchase;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SavePurchase implements ISavePurchase{
    private final String BASE_URL = "http://localhost:8080";
    private final ISavePurchase service;

    public SavePurchase() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(ISavePurchase.class);
    }

    @Override
    public Call<Purchase> savePurchase(Purchase purchase) {
        return service.savePurchase(purchase);
    }
    
}
