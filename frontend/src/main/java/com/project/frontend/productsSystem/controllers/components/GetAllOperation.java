package com.project.frontend.productsSystem.controllers.components;

import java.io.IOException;
import java.util.List;
import com.project.frontend.productsSystem.controllers.in.IGetAllOperation;
import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.ServiceProducts;
import com.project.frontend.shared.handlers.HandlerError;
import lombok.RequiredArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RequiredArgsConstructor
public class GetAllOperation implements IGetAllOperation {
    private final ServiceProducts serviceProducts;

    @Override
    public List<Product> getAllProducts() throws IOException {
        Call<List<Product>> call = serviceProducts.operation("GET_ALL");
        Response<List<Product>> response = call.execute();
        
        if (response.isSuccessful()) {
            return response.body();
        } else {
            HandlerError.manejarErrorAsync(response);
            return null;
        }
    }
}
