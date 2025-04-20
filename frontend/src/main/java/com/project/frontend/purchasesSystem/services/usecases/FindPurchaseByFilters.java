package com.project.frontend.purchasesSystem.services.usecases;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.project.frontend.purchasesSystem.models.Purchase;
import com.project.frontend.purchasesSystem.services.in.IFindPurchasesByFilters;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindPurchaseByFilters implements IFindPurchasesByFilters{

    private final String BASE_URL = "http://localhost:8080";
    private final IFindPurchasesByFilters service;

    public FindPurchaseByFilters() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = retrofit.create(IFindPurchasesByFilters.class);
    }

    @Override
    public Call<List<Purchase>> findByFilters(String nameClient, LocalDate date, LocalTime hour) {
        return this.service.findByFilters(nameClient, date, hour);
    }

    
    
}