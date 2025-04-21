package com.project.frontend.returnsSystem.services.in;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface IDeleteDevolution {
    @DELETE("/api/devolution/{id}")
    Call<Void> deleteDevolution(@Path("id") String id);
}