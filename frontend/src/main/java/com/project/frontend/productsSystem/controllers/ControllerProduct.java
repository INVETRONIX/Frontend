package com.project.frontend.productsSystem.controllers;

import java.io.IOException;
import com.project.frontend.productsSystem.controllers.components.DeleteOperation;
import com.project.frontend.productsSystem.controllers.components.GetByFiltersOperation;
import com.project.frontend.productsSystem.controllers.components.GetAllOperation;
import com.project.frontend.productsSystem.controllers.components.GetOperation;
import com.project.frontend.productsSystem.controllers.components.SaveOperation;
import com.project.frontend.productsSystem.controllers.components.UpdateOperation;
import com.project.frontend.productsSystem.controllers.in.IDeleteOperation;
import com.project.frontend.productsSystem.controllers.in.IGetAllOperation;
import com.project.frontend.productsSystem.controllers.in.IGetByFiltersOperation;
import com.project.frontend.productsSystem.controllers.in.IGetOperation;
import com.project.frontend.productsSystem.controllers.in.ISaveOperation;
import com.project.frontend.productsSystem.controllers.in.IUpdateOperation;
import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.ServiceProducts;

public class ControllerProduct {

    private final IDeleteOperation deleteOperation;
    private final IGetByFiltersOperation getByFiltersOperation;
    private final IGetAllOperation getAllOperation;
    private final IGetOperation getOperation;
    private final ISaveOperation saveOperation;
    private final IUpdateOperation updateOperation;

    public ControllerProduct() {
        ServiceProducts serviceProducts = ServiceProducts.createDefault();
        
        this.deleteOperation = new DeleteOperation(serviceProducts);
        this.getByFiltersOperation = new GetByFiltersOperation(serviceProducts);
        this.getAllOperation = new GetAllOperation(serviceProducts);
        this.getOperation = new GetOperation(serviceProducts);
        this.saveOperation = new SaveOperation(serviceProducts);
        this.updateOperation = new UpdateOperation(serviceProducts);
    }

    public Object operation(String query, Object... params) throws IOException {
        switch (query) {
            case "POST":
                return saveOperation.saveProduct((Product) params[0]);
            
            case "GET_BY_ID":
                return getOperation.getProductById((String) params[0]);
                
            case "GET_ALL":
                return getAllOperation.getAllProducts();
                
            case "UPDATE":
                return updateOperation.updateProduct((String) params[0], (Product) params[1]);
                
            case "DELETE":
                return deleteOperation.deleteProduct((String) params[0]);
                
            case "GET_BY_FILTERS":
                return getByFiltersOperation.findByFilters(
                    (String) params[0], 
                    (String) params[1], 
                    (String) params[2]
                );
                
            default:
                throw new IllegalArgumentException("Unknown operation: " + query);
        }
    }
}