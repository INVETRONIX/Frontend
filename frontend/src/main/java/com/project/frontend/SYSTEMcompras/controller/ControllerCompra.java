package com.project.frontend.SYSTEMcompras.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public ControllerCompra() {
        // Crear adaptadores personalizados para LocalDate y LocalTime
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();


        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

        apiService = retrofit.create(ICompraService.class);
        handlerErrorResponse = new HandlerErrorResponse();
    }

    // Adaptador para LocalDate
    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(src));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
                throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }

    // Adaptador para LocalTime
    private static class LocalTimeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

        @Override
        public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(src));
        }

        @Override
        public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
                throws JsonParseException {
            return LocalTime.parse(json.getAsString(), formatter);
        }
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