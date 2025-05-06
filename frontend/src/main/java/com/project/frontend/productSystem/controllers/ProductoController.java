package com.project.frontend.productSystem.controllers;

import com.project.frontend.productSystem.models.Producto;
import com.project.frontend.productSystem.services.IProductoService;
import com.project.frontend.productSystem.services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;
import java.util.List;

public class ProductoController {
    private final IProductoService productoService;

    public ProductoController() {
        productoService = RetrofitClient.getClient().create(IProductoService.class);
    }

    public List<Producto> obtenerTodos() throws Exception {
        Call<List<Producto>> call = productoService.obtenerTodosLosProductos();
        Response<List<Producto>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al obtener productos: " + response.message());
        }
    }

    public Producto obtenerPorId(Long id) throws Exception {
        Call<Producto> call = productoService.obtenerProductoPorId(id);
        Response<Producto> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al obtener producto: " + response.message());
        }
    }

    public Producto crear(Producto producto) throws Exception {
        Call<Producto> call = productoService.crearProducto(producto);
        Response<Producto> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al crear producto: " + response.message());
        }
    }

    public Producto actualizar(Long id, Producto producto) throws Exception {
        Call<Producto> call = productoService.actualizarProducto(id, producto);
        Response<Producto> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al actualizar producto: " + response.message());
        }
    }

    public void eliminar(Long id) throws Exception {
        Call<Void> call = productoService.eliminarProducto(id);
        Response<Void> response = call.execute();
        if (!response.isSuccessful()) {
            throw new Exception("Error al eliminar producto: " + response.message());
        }
    }

    public List<Producto> buscarPorNombre(String nombre) throws Exception {
        Call<List<Producto>> call = productoService.obtenerProductosPorNombre(nombre);
        Response<List<Producto>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al buscar productos por nombre: " + response.message());
        }
    }

    public List<Producto> buscarPorCategoria(String categoria) throws Exception {
        Call<List<Producto>> call = productoService.obtenerProductosPorCategoria(categoria);
        Response<List<Producto>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al buscar productos por categoría: " + response.message());
        }
    }

    public List<Producto> buscarPorProveedor(String proveedor) throws Exception {
        Call<List<Producto>> call = productoService.obtenerProductosPorProveedor(proveedor);
        Response<List<Producto>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al buscar productos por proveedor: " + response.message());
        }
    }

    public List<Producto> buscarPorFiltros(String nombre, String categoria, String proveedor) throws Exception {
        Call<List<Producto>> call = productoService.obtenerProductosPorFiltros(nombre, categoria, proveedor);
        Response<List<Producto>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Error al buscar productos por filtros: " + response.message());
        }
    }
} 