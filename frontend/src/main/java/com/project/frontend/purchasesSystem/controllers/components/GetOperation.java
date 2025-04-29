package com.project.frontend.purchasesSystem.controllers.components;

import java.io.IOException;
import java.util.List;
import com.project.frontend.purchasesSystem.controllers.in.IGetOperation;
import com.project.frontend.purchasesSystem.models.Purchase;
import com.project.frontend.purchasesSystem.services.ServicePurchases;
import com.project.frontend.shared.handlers.HandlerError;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public class GetOperation implements IGetOperation{
    private final ServicePurchases servicePurchases;

    @Override
    public Purchase getPurchaseById(String id) throws IOException {
        Call<Purchase> call = servicePurchases.operation("GET_BY_ID", id);
        Response<Purchase> response = call.execute();
        
        if (response.isSuccessful()) {
            return response.body();
        } else {
            HandlerError.manejarErrorAsync(response);
            return null;
        }
    }

    @Override
    public List<Purchase> findByFilters(String nameClient, String date, String hour) throws IOException {
        Call<List<Purchase>> call = servicePurchases.operation("GET_BY_FILTERS", nameClient, date, hour);
        Response<List<Purchase>> response = call.execute();
        
        if (response.isSuccessful()) {
            return response.body();
        } else {
            HandlerError.manejarErrorAsync(response);
            return null;
        }
    }

    @Override
    public List<Purchase> getAllPurchases() throws IOException {
        Call<List<Purchase>> call = servicePurchases.operation("GET_ALL");
        Response<List<Purchase>> response = call.execute();
        
        if (response.isSuccessful()) {
            return response.body();
        } else {
            HandlerError.manejarErrorAsync(response);
            return null;
        }
    }
    
}