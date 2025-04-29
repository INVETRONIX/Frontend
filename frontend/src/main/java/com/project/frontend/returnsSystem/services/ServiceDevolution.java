package com.project.frontend.returnsSystem.services;

import java.time.LocalDate;
import java.time.LocalTime;

import com.project.frontend.returnsSystem.models.Devolution;
import com.project.frontend.returnsSystem.services.in.IDeleteDevolution;
import com.project.frontend.returnsSystem.services.in.IFindDevolutionsByFilters;
import com.project.frontend.returnsSystem.services.in.IGetAllDevolutions;
import com.project.frontend.returnsSystem.services.in.IGetDevolutionById;
import com.project.frontend.returnsSystem.services.in.ISaveDevolution;
import com.project.frontend.returnsSystem.services.in.IUpdateDevolution;
import com.project.frontend.returnsSystem.services.usecases.DeleteDevolution;
import com.project.frontend.returnsSystem.services.usecases.FindDevolutionsByFilters;
import com.project.frontend.returnsSystem.services.usecases.GetAllDevolutions;
import com.project.frontend.returnsSystem.services.usecases.GetDevolutionById;
import com.project.frontend.returnsSystem.services.usecases.SaveDevolution;
import com.project.frontend.returnsSystem.services.usecases.UpdateDevolution;

import lombok.Builder;
import retrofit2.Call;

@Builder
public class ServiceDevolution {
    private final IDeleteDevolution deleteDevolution;
    private final IFindDevolutionsByFilters findDevolutionsByFilters;
    private final IGetAllDevolutions getAllDevolutions;
    private final IGetDevolutionById getDevolutionById;
    private final ISaveDevolution saveDevolution;
    private final IUpdateDevolution updateDevolution;

    public static ServiceDevolution createDefault(){
        return ServiceDevolution.builder()
        .deleteDevolution(new DeleteDevolution())
        .findDevolutionsByFilters(new FindDevolutionsByFilters())
        .getAllDevolutions(new GetAllDevolutions())
        .getDevolutionById(new GetDevolutionById())
        .saveDevolution(new SaveDevolution())
        .updateDevolution(new UpdateDevolution())
        .build();
    }

    @SuppressWarnings("unchecked")
    public <T> Call<T> operation(String query, Object... params) {
        switch (query) {
            case "POST":
                return (Call<T>) saveDevolution.saveDevolution((Devolution) params[0]);
            
            case "GET_BY_ID":
                return (Call<T>) getDevolutionById.getDevolutionById((String) params[0]);
                
            case "GET_ALL":
                return (Call<T>) getAllDevolutions.getAllDevolutions();
                
            case "UPDATE":
                return (Call<T>) updateDevolution.updateDevolution((String) params[0], (Devolution) params[1]);
                
            case "DELETE":
                return (Call<T>) deleteDevolution.deleteDevolution((String) params[0]);
                
            case "GET_BY_FILTERS":
                return (Call<T>) findDevolutionsByFilters.findByFilters(
                    (String) params[0], 
                    (LocalDate) params[1], 
                    (LocalTime) params[2]
                );
                
            default:
                throw new IllegalArgumentException("Unknown operation: " + query);
        }
    }
}
