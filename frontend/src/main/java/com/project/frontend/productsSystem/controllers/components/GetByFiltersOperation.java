package com.project.frontend.productsSystem.controllers.components;

import java.io.IOException;
import java.util.List;
import com.project.frontend.productsSystem.controllers.in.IGetByFiltersOperation;
import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.ServiceProducts;
import com.project.frontend.shared.handlers.HandlerError;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public class GetByFiltersOperation implements IGetByFiltersOperation {
    private final ServiceProducts serviceProducts;

    @Override
    public List<Product> findByFilters(String nameProduct, String category, String nameSupplier) throws IOException {
        Call<List<Product>> call = serviceProducts.operation("GET_BY_FILTERS", nameProduct, category, nameSupplier);
        Response<List<Product>> response = call.execute();
        
        if (response.isSuccessful()) {
            return response.body();
        } else {
            HandlerError.manejarErrorAsync(response);
            return null;
        }
    }
}
