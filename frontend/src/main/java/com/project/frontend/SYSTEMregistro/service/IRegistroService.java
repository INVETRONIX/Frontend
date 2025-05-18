package com.project.frontend.SYSTEMregistro.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import java.util.List;
import com.project.frontend.SYSTEMregistro.model.Usuario;

public interface IRegistroService {

    @POST("/api/usuarios")
    Call<Usuario> createUser(@Body Usuario usuario);

    @PUT("/api/usuarios/{id}")
    Call<Usuario> updateUser(@Path("id") Long id, @Body Usuario usuario);

    @DELETE("/api/usuarios/{id}")
    Call<Void> deleteUser(@Path("id") Long id);

    @GET("/api/usuarios/{id}")
    Call<Usuario> getUserById(@Path("id") Long id);

    @GET("/api/usuarios")
    Call<List<Usuario>> getAllUsers();

    @GET("/api/usuarios/nombre")
    Call<List<Usuario>> findByNombre(@Query("nombre") String nombre);

    @GET("/api/usuarios/edad")
    Call<List<Usuario>> findByEdad(@Query("edad") Integer edad);

    @GET("/api/usuarios/buscar")
    Call<List<Usuario>> findByFilters(
        @Query("nombre") String nombre,
        @Query("edad") Integer edad
    );
    
}