package com.project.frontend.productsSystem.services;

import java.util.List;
import com.project.frontend.productsSystem.models.Product;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProducts implements IServiceProducts{

    //agregamos el puerto donde esta corriendo el servicio
    private final String BASE_URL = "http://localhost:8080";

    //agregamos la capa de abstraccion para usar el servicio ya que ahí es donde estan las rutas de la api rest
    private final IServiceProducts apiService;

    //en este constructor se instancia retrofit con la url del puerto donde esta corriendo el servicio
    //tambien inicializamos el servicio, osea la capa de abstraccion
    public ServiceProducts(){
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.apiService = retrofit.create(IServiceProducts.class);
    }


    @Override
    public Call<Product> saveProduct(Product user) {
        return apiService.saveProduct(user);
    }


    @Override
    public Call<Product> getProductById(String id) {
        return apiService.getProductById(id);
    }


    @Override
    public Call<List<Product>> getAllProducts() {
        return apiService.getAllProducts();
    }


    @Override
    public Call<Product> updateProduct(String id, Product product) {
        return apiService.updateProduct(id, product);
    }


    @Override
    public Call<Void> deleteProduct(String id) {
        return apiService.deleteProduct(id);
    }


    @Override
    public Call<List<Product>> findByFilters(String nameProduct, String category, String nameSupplier) {
        return apiService.findByFilters(nameProduct, category, nameSupplier);
    } 

}