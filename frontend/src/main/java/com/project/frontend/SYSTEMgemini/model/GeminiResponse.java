package com.project.frontend.SYSTEMgemini.model;

import com.google.gson.annotations.SerializedName;

public class GeminiResponse {
    @SerializedName("analisis")
    private String analisis;

    @SerializedName("prediccion")
    private String prediccion;

    @SerializedName("recomendaciones")
    private String recomendaciones;

    public GeminiResponse(){}

    public String getAnalisis() {
        return analisis;
    }

    public void setAnalisis(String analisis) {
        this.analisis = analisis;
    }

    public String getPrediccion() {
        return prediccion;
    }

    public void setPrediccion(String prediccion) {
        this.prediccion = prediccion;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }
    
}