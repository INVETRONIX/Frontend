package com.project.frontend.productsSystem.services;

import java.util.List;

import com.project.frontend.productsSystem.models.Producto;
import com.project.frontend.suppliersSystem.models.Proveedor;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductosService implements IProductosService{
    private final String BASE_URL = "http://localhost:8080";
    private final IProductosService apiService;

    public ProductosService(){
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        this.apiService = retrofit.create(IProductosService.class);
    }

    @Override
    public Call<Producto> save(Producto producto) {
        return apiService.save(producto);
    }

    @Override
    public Call<Producto> findById(String id) {
        return apiService.findById(id);
    }

    @Override
    public Call<Void> deleteProducto(String id) {
        return apiService.deleteProducto(id);
    }

    @Override
    public Call<Producto> updateProducto(String id, Producto producto) {
        return apiService.updateProducto(id, producto);
    }

    @Override
    public Call<List<Producto>> findAll() {
        return apiService.findAll();
    }

    @Override
    public Call<List<Producto>> buscarProductos(String nombre, String descripcion, double precio, String categoria, int cantidadStock, Proveedor proveedor) {
        return apiService.buscarProductos(nombre, descripcion, precio, categoria, cantidadStock, proveedor);
    }
    
}
