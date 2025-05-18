package com.project.frontend.SYSTEMproductos.controller;

import com.project.frontend.SYSTEMproductos.model.Producto;
import com.project.frontend.SYSTEMproductos.service.IProductoService;
import com.project.frontend.core.BackendException;
import com.project.frontend.core.HandlerErrorResponse;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ControllerProducto {
    private static final String BASE_URL = "http://localhost:8080";
    private final IProductoService apiService;
    private final HandlerErrorResponse handlerErrorResponse;

    public ControllerProducto(){
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        apiService = retrofit.create(IProductoService.class);
        handlerErrorResponse = new HandlerErrorResponse();
    }

    public List<Producto> getAllProductos() throws IOException, BackendException{
        Response<List<Producto>> response = apiService.getAllProductos().execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public Optional<Producto> getProductoById(Long id) throws IOException, BackendException{
        Response<Producto> response = apiService.getProductoById(id).execute();
        if(response.isSuccessful()){
            return Optional.of(response.body());
        }
        handlerErrorResponse.handleErrorResponse(response);
        return Optional.empty();
    }

    public Producto createProducto(Producto producto) throws IOException, BackendException{
        Response<Producto> response = apiService.createProducto(producto).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public Optional<Producto> updateProducto(Long id, Producto producto) throws IOException, BackendException{
        Response<Producto> response = apiService.updateProducto(id, producto).execute();
        if(response.isSuccessful()){
            return Optional.of(response.body());
        }
        handlerErrorResponse.handleErrorResponse(response);
        return Optional.empty();
    }

    public boolean deleteProducto(Long id) throws IOException, BackendException{
        Response<Void> response = apiService.deleteProducto(id).execute();
        if(response.isSuccessful()){
            return true;
        }
        handlerErrorResponse.handleErrorResponse(response);
        return false;
    }

    public List<Producto> findByNombre(String nombre) throws IOException, BackendException{
        Response<List<Producto>> response = apiService.findByNombre(nombre).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public List<Producto> findByPrecio(Double precio) throws IOException, BackendException{
        Response<List<Producto>> response = apiService.findByPrecio(precio).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public List<Producto> findByStock(Integer stock) throws IOException, BackendException{
        Response<List<Producto>> response = apiService.findByStock(stock).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public List<Producto> findByFilters(String nombre, Double precio, Integer stock) throws IOException, BackendException{
        Response<List<Producto>> response = apiService.findByFilters(nombre, precio, stock).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }
}
