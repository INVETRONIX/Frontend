package com.project.frontend.purchasesSystem.controllers;

import java.io.IOException;

import com.project.frontend.purchasesSystem.controllers.components.DeleteOperation;
import com.project.frontend.purchasesSystem.controllers.components.GetOperation;
import com.project.frontend.purchasesSystem.controllers.components.SaveOperation;
import com.project.frontend.purchasesSystem.controllers.components.UpdateOperation;
import com.project.frontend.purchasesSystem.controllers.in.IDeleteOperation;
import com.project.frontend.purchasesSystem.controllers.in.IGetOperation;
import com.project.frontend.purchasesSystem.controllers.in.ISaveOperation;
import com.project.frontend.purchasesSystem.controllers.in.IUpdateOperation;
import com.project.frontend.purchasesSystem.models.Purchase;
import com.project.frontend.purchasesSystem.services.ServicePurchases;

public class ControllerPurchase {
    private final IDeleteOperation deleteOperation;
    private final IGetOperation getOperation;
    private final ISaveOperation saveOperation;
    private final IUpdateOperation updateOperation;

    public ControllerPurchase() {
        ServicePurchases servicePurchases = ServicePurchases.createDefault();
        
        this.deleteOperation = new DeleteOperation(servicePurchases);
        this.getOperation = new GetOperation(servicePurchases);
        this.saveOperation = new SaveOperation(servicePurchases);
        this.updateOperation = new UpdateOperation(servicePurchases);
    }

    public Object operation(String query, Object... params) throws IOException {
        switch (query) {
            case "POST":
                return saveOperation.savePurchase((Purchase) params[0]);
            
            case "GET_BY_ID":
                return getOperation.getPurchaseById((String) params[0]);
                
            case "GET_ALL":
                return getOperation.getAllPurchases();
                
            case "UPDATE":
                return updateOperation.updatePurchase((String) params[0], (Purchase) params[1]);
                
            case "DELETE":
                return deleteOperation.deletePurchase((String) params[0]);
                
            case "GET_BY_FILTERS":
                return getOperation.findByFilters(
                    (String) params[0], 
                    (String) params[1], 
                    (String) params[2]
                );
                
            default:
                throw new IllegalArgumentException("Unknown operation: " + query);
        }
    }
}