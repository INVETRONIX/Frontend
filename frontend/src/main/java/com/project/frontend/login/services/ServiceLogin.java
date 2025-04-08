package com.project.frontend.login.services;

import com.project.frontend.registerUsers.models.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLogin implements IServiceLogin{
    
    //agregamos el puerto donde esta corriendo el servicio
    private final String BASE_URL = "http://localhost:8080";

    //agregamos la capa de abstraccion para usar el servicio ya que ahí es donde estan las rutas de la api rest
    private final IServiceLogin apiService;


    //en este constructor se instancia retrofit con la url del puerto donde esta corriendo el servicio
    //tambien inicializamos el servicio, osea la capa de abstraccion
    public ServiceLogin(){
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.apiService = retrofit.create(IServiceLogin.class);
    }


    //metodo sobreescrito donde llama al de la interfaz y le manda los datos para ser enviados al back
    @Override
    public Call<User> login(String correo, String password) {
      return apiService.login(correo, password);
    }

    
}
