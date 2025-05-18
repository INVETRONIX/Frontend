package com.project.frontend.SYSTEMcompras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.project.frontend.SYSTEMcompras.model.Compra;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ICompraService {
    @GET("/api/compras")
    Call<List<Compra>> getAllCompras();

    @GET("/api/compras/{id}")
    Call<Compra> getCompraById(@Path("id") Long id);

    @POST("/api/compras")
    Call<Compra> createCompra(@Body Compra compra);

    @PUT("/api/compras/{id}")
    Call<Compra> updateCompra(@Path("id") Long id, @Body Compra compra);

    @DELETE("/api/compras/{id}")
    Call<Void> deleteCompra(@Path("id") Long id);

    @GET("/api/compras/fecha")
    Call<List<Compra>> findByFecha(@Query("fecha") LocalDate fecha);

    @GET("/api/compras/usuario/{id}")
    Call<List<Compra>> findByUsuarioId(@Path("id") Long id);

    @GET("/api/compras/hora")
    Call<List<Compra>> findByHora(@Query("hora") LocalTime hora);

    @GET("/api/compras/buscar")
    Call<List<Compra>> findByFilters(
        @Query("fecha") LocalDate fecha,
        @Query("usuarioId") Long usuarioId,
        @Query("hora") LocalTime hora
    );
    
}
