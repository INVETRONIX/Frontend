package com.project.frontend.comprasSystem.services;

import java.util.List;
import com.project.frontend.comprasSystem.models.Compra;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ICompraService {
    @DELETE("/api/compras/{id}")
    Call<Void> eliminarCompra(@Path("id") Long id);

    @GET("/api/compras")
    Call<List<Compra>> obtenerTodasLasCompras();

    @PUT("/api/compras/{id}")
    Call<Compra> actualizarCompra(@Path("id") Long id, @Body Compra compra);

    @POST("/api/compras")
    Call<Compra> crearCompra(@Body Compra compra);

    @GET("/api/compras/{id}")
    Call<Compra> obtenerCompraPorId(@Path("id") Long id);

    @GET("/api/compras/usuario/{usuarioId}")
    Call<List<Compra>> obtenerComprasPorUsuario(@Path("usuarioId") Long usuarioId);

    @GET("/api/compras/filtros")
    Call<List<Compra>> obtenerComprasPorFiltros(
        @Query("usuarioId") Long usuarioId,
        @Query("fechaInicio") String fechaInicio,
        @Query("fechaFin") String fechaFin,
        @Query("totalMin") Double totalMin,
        @Query("totalMax") Double totalMax
    );
} 