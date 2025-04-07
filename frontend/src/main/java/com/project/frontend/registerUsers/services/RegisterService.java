package com.project.frontend.registerUsers.services;

import com.project.frontend.registerUsers.models.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterService implements IRegisterService {
    private final String BASE_URL = "http://localhost:8080";
    private final IRegisterService apiService;

    public RegisterService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.apiService = retrofit.create(IRegisterService.class);
    }

    @Override
    public Call<User> save(User user) {
        return apiService.save(user);
    }

    @Override
    public Call<User> findById(String id) {
        return apiService.findById(id);
    }

}