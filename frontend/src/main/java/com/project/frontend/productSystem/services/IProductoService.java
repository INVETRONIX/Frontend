package com.project.frontend.productSystem.services;

import java.util.List;
import com.project.frontend.productSystem.models.Producto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IProductoService {
    @DELETE("/api/productos/{id}")
    Call<Void> eliminarProducto(@Path("id") Long id);

    @GET("/api/productos")
    Call<List<Producto>> obtenerTodosLosProductos();
    

    @PUT("/api/productos/{id}")
    Call<Producto> actualizarProducto(@Path("id") Long id, @Body Producto producto);

    @POST("/api/productos")
    Call<Producto> crearProducto(@Body Producto producto);

    @GET("/api/productos/{id}")
    Call<Producto> obtenerProductoPorId(@Path("id") Long id);

    @GET("/api/productos/nombre/{nombre}")
    Call<List<Producto>> obtenerProductosPorNombre(@Path("nombre") String nombre);

    @GET("/api/productos/categoria/{categoria}")
    Call<List<Producto>> obtenerProductosPorCategoria(@Path("categoria") String categoria);

    @GET("/api/productos/proveedor/{proveedor}")
    Call<List<Producto>> obtenerProductosPorProveedor(@Path("proveedor") String proveedor);
    
    @GET("/api/productos/filtros")
    Call<List<Producto>> obtenerProductosPorFiltros(@Query("nombre") String nombre, @Query("categoria") String categoria, @Query("proveedor") String proveedor);
}