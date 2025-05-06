package com.project.frontend.comprasSystem.services;

import com.project.frontend.productSystem.models.Producto;
import com.project.frontend.productSystem.services.IProductoService;
import com.project.frontend.productSystem.services.RetrofitClient;
import retrofit2.Response;
import java.util.List;

public class ProductoClienteService {
    private final IProductoService productoService;

    public ProductoClienteService() {
        this.productoService = RetrofitClient.getClient().create(IProductoService.class);
    }

    public List<Producto> obtenerTodos() throws Exception {
        Response<List<Producto>> response = productoService.obtenerTodosLosProductos().execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al obtener productos: " + response.errorBody().string());
        }
    }
} 