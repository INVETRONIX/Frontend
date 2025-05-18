package com.project.frontend.core;

public class BackendException extends RuntimeException {
    private final int statusCode;
    private final String mensaje;

    public BackendException(int statusCode, String mensaje) {
        super(mensaje);
        this.statusCode = statusCode;
        this.mensaje = mensaje;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMensaje() {
        return mensaje;
    }
} 
