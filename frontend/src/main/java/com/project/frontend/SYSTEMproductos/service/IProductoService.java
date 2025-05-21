package com.project.frontend.SYSTEMproductos.service;

import java.util.List;
import com.project.frontend.SYSTEMproductos.model.Producto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IProductoService {
    
    @POST("/api/productos")
    Call<Producto> createProducto(@Body Producto producto);

    @PUT("/api/productos/{id}")
    Call<Producto> updateProducto(@Path("id") Long id, @Body Producto producto);

    @DELETE("/api/productos/{id}")
    Call<Void> deleteProducto(@Path("id") Long id);

    @GET("/api/productos/{id}")
    Call<Producto> getProductoById(@Path("id") Long id);

    @GET("/api/productos")
    Call<List<Producto>> getAllProductos();

    @GET("/api/productos/buscar/nombre")
    Call<List<Producto>> findByNombre(@Query("nombre") String nombre);

    @GET("/api/productos/buscar/precio")
    Call<List<Producto>> findByPrecio(@Query("precio") Double precio);

    @GET("/api/productos/buscar/stock")
    Call<List<Producto>> findByStock(@Query("stock") Integer stock);

    @GET("/api/productos/buscar")
    Call<List<Producto>> findByFilters(@Query("nombre") String nombre, @Query("precio") Double precio, @Query("stock") Integer stock);
    
}
