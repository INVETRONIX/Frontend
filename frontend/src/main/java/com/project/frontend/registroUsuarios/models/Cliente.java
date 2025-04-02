package com.project.frontend.registroUsuarios.models;


public class Cliente extends User{
    private String telefone;
    private int edad;

    public Cliente (String name, String email, String password,String telefone, int edad){
        super(name, email, password);
        this.telefone = telefone;
        this.edad = edad;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String toString(){
        return "Cliente{" +
        "telefono='" + telefone + '\'' +
        ", edad='" + edad +
        '}';
    }
}
