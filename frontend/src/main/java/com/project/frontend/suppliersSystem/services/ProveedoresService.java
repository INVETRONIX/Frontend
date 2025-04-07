package com.project.frontend.suppliersSystem.services;

import java.util.List;

import com.project.frontend.shared.Almacen;
import com.project.frontend.suppliersSystem.models.Proveedor;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProveedoresService implements IProveedoresService{
    private final String BASE_URL = "http://localhost:8080";
    private final IProveedoresService apiService;

    public ProveedoresService(){
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        this.apiService = retrofit.create(IProveedoresService.class);
    }

    @Override
    public Call<Proveedor> save(Proveedor proveedor){
        return apiService.save(proveedor);
    }

    @Override
    public Call<Proveedor> findById(String id){
        return apiService.findById(id);
    }

    @Override
    public Call<Void> deleteProveedor(String id){
        return apiService.deleteProveedor(id);
    }

    @Override
    public Call<Proveedor> updateProveedor(String id, Proveedor proveedor) {
        return apiService.updateProveedor(id, proveedor);
    }

    @Override
    public Call<List<Proveedor>> findAll() {
        return apiService.findAll();
    }

    @Override
    public Call<List<Proveedor>> buscarProveedores(String id, String nombre, Almacen almacen, String telefono,
            String email) {
        return apiService.buscarProveedores(id, nombre, almacen, telefono, email);
    }
}