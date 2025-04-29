package com.project.frontend.returnsSystem.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import com.project.frontend.returnsSystem.controllers.components.DeleteOperation;
import com.project.frontend.returnsSystem.controllers.components.GetOPeration;
import com.project.frontend.returnsSystem.controllers.components.SaveOperation;
import com.project.frontend.returnsSystem.controllers.components.UpdateOperation;
import com.project.frontend.returnsSystem.controllers.in.IDeleteOperation;
import com.project.frontend.returnsSystem.controllers.in.IGetOperation;
import com.project.frontend.returnsSystem.controllers.in.ISaveOperation;
import com.project.frontend.returnsSystem.controllers.in.IUpdateOperation;
import com.project.frontend.returnsSystem.models.Devolution;
import com.project.frontend.returnsSystem.services.ServiceDevolution;

public class ControllerDevolution {

    private final IDeleteOperation deleteOperation;
    private final IGetOperation getOperation;
    private final ISaveOperation saveOperation;
    private final IUpdateOperation updateOperation;


    public ControllerDevolution() {
        ServiceDevolution serviceDevolution = ServiceDevolution.createDefault();

        this.deleteOperation= new DeleteOperation(serviceDevolution);
        this.getOperation= new GetOPeration(serviceDevolution);
        this.saveOperation= new SaveOperation(serviceDevolution);
        this.updateOperation= new UpdateOperation(serviceDevolution);
    }

    public Object operation(String query, Object... params) throws IOException {
        switch (query) {
            case "POST":
                return saveOperation.saveDevolution((Devolution) params[0]);
            
            case "GET_BY_ID":
                return getOperation.getDevolutionById((String) params[0]);
                
            case "GET_ALL":
                return getOperation.getAllDevolutions();
                
            case "UPDATE":
                return updateOperation.updateDevolution((String) params[0], (Devolution) params[1]);
                
            case "DELETE":
                return deleteOperation.deleteProduct((String) params[0]);
                
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