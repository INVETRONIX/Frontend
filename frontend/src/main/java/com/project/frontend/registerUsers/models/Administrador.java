package com.project.frontend.registerUsers.models;

public class Administrador extends User {
    private String experiencia;
    private String sueldo;

    public Administrador(String name, String email, String password, String experiencia, String sueldo) {
        super(name, email, password);
        this.experiencia = experiencia;
        this.sueldo = sueldo;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getSueldo() {
        return sueldo;
    }

    public void setSueldo(String sueldo) {
        this.sueldo = sueldo;
    }
    
    public String toString(){
        return "Administrador{" +
        "experiencia='" + experiencia + '\'' +
        ", sueldo='" + sueldo +
        '}';
    }
    
}
