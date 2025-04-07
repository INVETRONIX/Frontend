package com.project.frontend.productsSystem.services;

import java.util.List;

import com.project.frontend.productsSystem.models.Producto;
import com.project.frontend.suppliersSystem.models.Proveedor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IProductosService {
    @POST("api/usuarios/productos")
    Call<Producto> save(@Body Producto producto);

    @GET("api/usuarios/productos/{id}")
    Call<Producto> findById(@Path("id") String id);

    @DELETE("api/usuarios/productos/{id}")
    Call<Void> deleteProducto(@Path("id") String id);

    @PUT("api/usuarios/productos/{id}")
    Call<Producto> updateProducto(@Path("id") String id,@Body Producto producto);

    @GET("api/usuarios/productos")
    Call<List<Producto>> findAll();

    @GET("api/usuarios/buscar")
    Call<List<Producto>> buscarProductos(
        @Query("nombre") String nombre,
        @Query("descripcion") String descripcion,
        @Query("precio") double precio,
        @Query("categoria") String categoria,
        @Query("cantidadStock") int cantidadStock,
        @Query("proveedor") Proveedor proveedor
        );
}
