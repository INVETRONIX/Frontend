package com.project.frontend.login.services;

import com.project.frontend.registroUsuarios.models.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginService implements ILoginService{

    private final String BASE_URL = "http://localhost:8080";
    private final ILoginService apiService;

     public LoginService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.apiService = retrofit.create(ILoginService.class);
    }

    @Override
    public Call<User> login(String correo, String contraseña) {
       return apiService.login(correo, contraseña);
    }
    
}
