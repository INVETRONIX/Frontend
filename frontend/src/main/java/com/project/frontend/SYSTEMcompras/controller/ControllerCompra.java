package com.project.frontend.SYSTEMcompras.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import com.project.frontend.SYSTEMcompras.model.Compra;
import com.project.frontend.SYSTEMcompras.service.ICompraService;
import com.project.frontend.core.BackendException;
import com.project.frontend.core.HandlerErrorResponse;
import com.project.frontend.core.tokenManager.Auth;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerCompra {
    private static final String BASE_URL = "http://localhost:8080";
    private final ICompraService apiService;
    private final HandlerErrorResponse handlerErrorResponse;

    public ControllerCompra(){

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Auth())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        apiService = retrofit.create(ICompraService.class);
        handlerErrorResponse = new HandlerErrorResponse();
    }

    public List<Compra> getAllCompras() throws IOException, BackendException{
        Response<List<Compra>> response = apiService.getAllCompras().execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public Optional<Compra> getCompraById(Long id) throws IOException, BackendException{
        Response<Compra> response = apiService.getCompraById(id).execute();
        if(response.isSuccessful()){
            return Optional.of(response.body());
        }
        handlerErrorResponse.handleErrorResponse(response);
        return Optional.empty();
    }

    public Compra createCompra(Compra compra) throws IOException, BackendException{
        Response<Compra> response = apiService.createCompra(compra).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public Optional<Compra> updateCompra(Long id, Compra compra) throws IOException, BackendException{
        Response<Compra> response = apiService.updateCompra(id, compra).execute();
        if(response.isSuccessful()){
            return Optional.of(response.body());
        }
        handlerErrorResponse.handleErrorResponse(response);
        return Optional.empty();
    }

    public boolean deleteCompra(Long id) throws IOException, BackendException{
        Response<Void> response = apiService.deleteCompra(id).execute();
        if(response.isSuccessful()){
            return true;
        }
        handlerErrorResponse.handleErrorResponse(response);
        return false;
    }

    public List<Compra> findByFecha(LocalDate fecha) throws IOException, BackendException{
        Response<List<Compra>> response = apiService.findByFecha(fecha).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public List<Compra> findByUsuario(Long idUsuario) throws IOException, BackendException{
        Response<List<Compra>> response = apiService.findByUsuarioId(idUsuario).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public List<Compra> findByHora(LocalTime hora) throws IOException, BackendException{
        Response<List<Compra>> response = apiService.findByHora(hora).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public List<Compra> findByFilters(LocalDate fecha, Long idUsuario, LocalTime hora) throws IOException, BackendException{
        Response<List<Compra>> response = apiService.findByFilters(fecha, idUsuario, hora).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }
    
}