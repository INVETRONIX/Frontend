package com.project.frontend.returnsSystem.controllers.components;

import java.io.IOException;
import java.util.List;
import com.project.frontend.returnsSystem.controllers.in.IGetOperation;
import com.project.frontend.returnsSystem.models.Devolution;
import com.project.frontend.returnsSystem.services.ServiceDevolution;
import com.project.frontend.shared.handlers.HandlerError;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public class GetOPeration implements IGetOperation{
    private final ServiceDevolution serviceDevolution;

    @Override
    public Devolution getDevolutionById(String id) throws IOException {
        Call<Devolution> call = serviceDevolution.operation("GET_BY_ID", id);
        Response<Devolution> response = call.execute();
        
        if (response.isSuccessful()) {
            return response.body();
        } else {
            HandlerError.manejarErrorAsync(response);
            return null;
        }
    }

    @Override
    public List<Devolution> findByFilters(String nameClient, String date, String hour) throws IOException {
       Call<List<Devolution>> call = serviceDevolution.operation("GET_BY_FILTERS", nameClient, date, hour);
        Response<List<Devolution>> response = call.execute();
        
        if (response.isSuccessful()) {
            return response.body();
        } else {
            HandlerError.manejarErrorAsync(response);
            return null;
        }
    }

    @Override
    public List<Devolution> getAllDevolutions() throws IOException {
        Call<List<Devolution>> call = serviceDevolution.operation("GET_ALL");
        Response<List<Devolution>> response = call.execute();
        
        if (response.isSuccessful()) {
            return response.body();
        } else {
            HandlerError.manejarErrorAsync(response);
            return null;
        }
    }

}