package com.project.frontend.comprasSystem.controllers;

import com.project.frontend.comprasSystem.models.Compra;
import com.project.frontend.comprasSystem.services.ICompraService;
import com.project.frontend.comprasSystem.services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;
import java.util.List;

public class CompraController {
    private final ICompraService compraService;

    public CompraController() {
        compraService = RetrofitClient.getClient().create(ICompraService.class);
    }

    public List<Compra> obtenerTodas() throws Exception {
        Call<List<Compra>> call = compraService.obtenerTodasLasCompras();
        Response<List<Compra>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al obtener compras: " + response.message());
        }
    }

    public Compra obtenerPorId(Long id) throws Exception {
        Call<Compra> call = compraService.obtenerCompraPorId(id);
        Response<Compra> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al obtener compra: " + response.message());
        }
    }

    public Compra crear(Compra compra) throws Exception {
        Call<Compra> call = compraService.crearCompra(compra);
        Response<Compra> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al crear compra: " + response.message());
        }
    }

    public Compra actualizar(Long id, Compra compra) throws Exception {
        Call<Compra> call = compraService.actualizarCompra(id, compra);
        Response<Compra> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al actualizar compra: " + response.message());
        }
    }

    public void eliminar(Long id) throws Exception {
        Call<Void> call = compraService.eliminarCompra(id);
        Response<Void> response = call.execute();
        if (!response.isSuccessful()) {
            throw new Exception("Error al eliminar compra: " + response.message());
        }
    }

    public List<Compra> buscarPorUsuario(Long usuarioId) throws Exception {
        Call<List<Compra>> call = compraService.obtenerComprasPorUsuario(usuarioId);
        Response<List<Compra>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al buscar compras por usuario: " + response.message());
        }
    }

    public List<Compra> buscarPorFiltros(Long usuarioId, String fechaInicio, String fechaFin, Double totalMin, Double totalMax) throws Exception {
        Call<List<Compra>> call = compraService.obtenerComprasPorFiltros(usuarioId, fechaInicio, fechaFin, totalMin, totalMax);
        Response<List<Compra>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al buscar compras por filtros: " + response.message());
        }
    }
} 