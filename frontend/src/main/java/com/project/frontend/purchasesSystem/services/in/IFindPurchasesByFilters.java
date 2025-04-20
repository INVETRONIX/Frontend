package com.project.frontend.purchasesSystem.services.in;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.project.frontend.purchasesSystem.models.Purchase;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFindPurchasesByFilters {

    @GET("api/purchase/search")
    Call<List<Purchase>> findByFilters(
        @Query("nameClient") String nameCliente,
        @Query("hour") LocalTime hour,
        @Query("date") LocalDate date
    );
}
