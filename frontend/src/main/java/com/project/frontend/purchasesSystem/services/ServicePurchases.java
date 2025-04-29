package com.project.frontend.purchasesSystem.services;

import java.time.LocalDate;
import java.time.LocalTime;
import com.project.frontend.purchasesSystem.models.Purchase;
import com.project.frontend.purchasesSystem.services.in.IDeletePurchase;
import com.project.frontend.purchasesSystem.services.in.IFindPurchasesByFilters;
import com.project.frontend.purchasesSystem.services.in.IGetAllPurchases;
import com.project.frontend.purchasesSystem.services.in.IGetPurchaseById;
import com.project.frontend.purchasesSystem.services.in.ISavePurchase;
import com.project.frontend.purchasesSystem.services.in.IUpdatePurchase;
import com.project.frontend.purchasesSystem.services.usecases.DeletePurchase;
import com.project.frontend.purchasesSystem.services.usecases.FindPurchaseByFilters;
import com.project.frontend.purchasesSystem.services.usecases.GetAllPurchases;
import com.project.frontend.purchasesSystem.services.usecases.GetPurchaseById;
import com.project.frontend.purchasesSystem.services.usecases.SavePurchase;
import com.project.frontend.purchasesSystem.services.usecases.UpdatePurchase;
import lombok.Builder;
import retrofit2.Call;

@Builder
public class ServicePurchases {
    private final IDeletePurchase deletePurchase;
    private final IFindPurchasesByFilters findPurchasesByFilters;
    private final IGetAllPurchases getAllPurchases;
    private final IGetPurchaseById getPurchaseById;
    private final ISavePurchase savePurchase;
    private final IUpdatePurchase updatePurchase;


    // Factory method to create a fully initialized servicePurchase
    public static ServicePurchases createDefault() {
        return ServicePurchases.builder()
        .deletePurchase(new DeletePurchase())
        .findPurchasesByFilters(new FindPurchaseByFilters())
        .getAllPurchases(new GetAllPurchases())
        .getPurchaseById(new GetPurchaseById())
        .savePurchase(new SavePurchase())
        .updatePurchase(new UpdatePurchase())
        .build();
    }

    @SuppressWarnings("unchecked")
    public <T> Call<T> operation(String query, Object... params) {
        switch (query) {
            case "POST":
                return (Call<T>) savePurchase.savePurchase((Purchase) params[0]);
            
            case "GET_BY_ID":
                return (Call<T>) getPurchaseById.getPurchaseById((String) params[0]);
                
            case "GET_ALL":
                return (Call<T>) getAllPurchases.getAllPurchases();
                
            case "UPDATE":
                return (Call<T>) updatePurchase.updatePurchase((String) params[0], (Purchase) params[1]);
                
            case "DELETE":
                return (Call<T>) deletePurchase.deletePurchase((String) params[0]);
                
            case "GET_BY_FILTERS":
                return (Call<T>) findPurchasesByFilters.findByFilters(
                    (String) params[0], 
                    (String) params[1], 
                    (String) params[2]
                );
                
            default:
                throw new IllegalArgumentException("Unknown operation: " + query);
        }
    }

}
