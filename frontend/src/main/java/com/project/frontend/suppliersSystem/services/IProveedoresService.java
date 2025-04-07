package com.project.frontend.suppliersSystem.services;

import java.util.List;

import com.project.frontend.shared.Almacen;
import com.project.frontend.suppliersSystem.models.Proveedor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IProveedoresService {

    @POST("api/usuarios/proveedores")
    Call<Proveedor> save(@Body Proveedor proveedor);

    @GET("api/usuarios/proveedores/{id}")
    Call<Proveedor> findById(@Path ("id") String id);

    @DELETE("api/usuarios/proveedores/{id}")
    Call<Void> deleteProveedor(@Path ("id") String id);

    @PUT("api/usuarios/proveedores/{id}")
    Call<Proveedor> updateProveedor(@Path ("id") String id,@Body Proveedor proveedor);

    @GET("api/usuarios/proveedores")
    Call<List<Proveedor>> findAll();

    @GET("api/usuarios/buscar")
    Call<List<Proveedor>> buscarProveedores(
        @Query("id") String id,
        @Query("nombre") String nombre,
        @Query("almacen") Almacen almacen,
        @Query("telefono") String telefono,
        @Query("email") String email
    );
}
