package com.project.frontend.SYSTEMregistro.controller;

import com.project.frontend.SYSTEMregistro.model.Usuario;
import com.project.frontend.SYSTEMregistro.service.IRegistroService;
import com.project.frontend.core.BackendException;
import com.project.frontend.core.HandlerErrorResponse;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ControllerUsuario {
    private static final String BASE_URL = "http://localhost:8080";
    private final IRegistroService apiService;
    private final HandlerErrorResponse handlerErrorResponse;

    public ControllerUsuario(){
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        apiService = retrofit.create(IRegistroService.class);
        handlerErrorResponse = new HandlerErrorResponse();
    }

    public List<Usuario> getAllUsers() throws IOException, BackendException{
        Response<List<Usuario>> response = apiService.getAllUsers().execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public Optional<Usuario> getUserById(Long id) throws IOException, BackendException{
        Response<Usuario> response = apiService.getUserById(id).execute();
        if(response.isSuccessful()){
            return Optional.of(response.body());
        }
        handlerErrorResponse.handleErrorResponse(response);
        return Optional.empty();
    }

    public Usuario createUser(Usuario usuario) throws IOException, BackendException{
        Response<Usuario> response = apiService.createUser(usuario).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public Optional<Usuario> updateUser(Long id, Usuario usuario) throws IOException, BackendException{
        Response<Usuario> response = apiService.updateUser(id, usuario).execute();
        if(response.isSuccessful()){
            return Optional.of(response.body());
        }
        handlerErrorResponse.handleErrorResponse(response);
        return Optional.empty();
    }

    public boolean deleteUser(Long id) throws IOException, BackendException{
        Response<Void> response = apiService.deleteUser(id).execute();
        if(response.isSuccessful()){
            return true;
        }
        handlerErrorResponse.handleErrorResponse(response);
        return false;
    }

    public List<Usuario> findByNombre(String nombre) throws IOException, BackendException{
        Response<List<Usuario>> response = apiService.findByNombre(nombre).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public List<Usuario> findByEdad(Integer edad) throws IOException, BackendException{
        Response<List<Usuario>> response = apiService.findByEdad(edad).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

    public List<Usuario> findByFilters(String nombre, Integer edad) throws IOException, BackendException{
        Response<List<Usuario>> response = apiService.findByFilters(nombre, edad).execute();
        if(response.isSuccessful()){
            return response.body();
        }
        handlerErrorResponse.handleErrorResponse(response);
        return null;
    }

}