package com.project.frontend.productsSystem.services;

import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.productsSystem.services.in.IDeleteProduct;
import com.project.frontend.productsSystem.services.in.IFindProductsByFilters;
import com.project.frontend.productsSystem.services.in.IGetAllProducts;
import com.project.frontend.productsSystem.services.in.IGetProductById;
import com.project.frontend.productsSystem.services.in.ISaveProduct;
import com.project.frontend.productsSystem.services.in.IUpdateProduct;
import com.project.frontend.productsSystem.services.usecases.DeleteProduct;
import com.project.frontend.productsSystem.services.usecases.FindProductsByFilters;
import com.project.frontend.productsSystem.services.usecases.GetAllProducts;
import com.project.frontend.productsSystem.services.usecases.GetProductById;
import com.project.frontend.productsSystem.services.usecases.SaveProduct;
import com.project.frontend.productsSystem.services.usecases.UpdateProduct;
import lombok.Builder;
import retrofit2.Call;

@Builder
public class ServiceProducts {
    private final IDeleteProduct deleteProduct;
    private final IFindProductsByFilters findProductsByFilters;
    private final IGetAllProducts getAllProducts;
    private final IGetProductById getProductById;
    private final ISaveProduct saveProduct;
    private final IUpdateProduct updateProduct;
    
    // Factory method to create a fully initialized ServiceProducts
    public static ServiceProducts createDefault() {
        return ServiceProducts.builder()
            .deleteProduct(new DeleteProduct())
            .findProductsByFilters(new FindProductsByFilters())
            .getAllProducts(new GetAllProducts())
            .getProductById(new GetProductById())
            .saveProduct(new SaveProduct())
            .updateProduct(new UpdateProduct())
            .build();
    }

    @SuppressWarnings("unchecked")
    public <T> Call<T> operation(String query, Object... params) {
        switch (query) {
            case "POST":
                return (Call<T>) saveProduct.saveProduct((Product) params[0]);
            
            case "GET_BY_ID":
                return (Call<T>) getProductById.getProductById((String) params[0]);
                
            case "GET_ALL":
                return (Call<T>) getAllProducts.getAllProducts();
                
            case "UPDATE":
                return (Call<T>) updateProduct.updateProduct((String) params[0], (Product) params[1]);
                
            case "DELETE":
                return (Call<T>) deleteProduct.deleteProduct((String) params[0]);
                
            case "GET_BY_FILTERS":
                return (Call<T>) findProductsByFilters.findByFilters(
                    (String) params[0], 
                    (String) params[1], 
                    (String) params[2]
                );
                
            default:
                throw new IllegalArgumentException("Unknown operation: " + query);
        }
    }
}