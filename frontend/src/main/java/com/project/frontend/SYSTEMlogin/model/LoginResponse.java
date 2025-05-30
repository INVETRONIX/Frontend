package com.project.frontend.SYSTEMlogin.model;

import com.google.gson.annotations.SerializedName;
import com.project.frontend.SYSTEMregistro.model.Usuario;

public class LoginResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("usuario")
    private Usuario usuario;

    public LoginResponse() {}

    public LoginResponse(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}