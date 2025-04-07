package com.project.frontend.suppliersSystem.models;

import com.project.frontend.shared.Store;

public class Supplier {
    private String id;
    private String name;
    private Store store;
    private String phone;
    private String email;

    public Supplier(String id, String name, Store store, String phone, String email){
        this.id = id;
        this.name = name;
        this.store = store;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getPhone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return "Supplier{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        "Store{" +
            "nit='" + store.getNit() + '\'' +
            ", productos='" + store.getProducts() + '\'' +
            ", nombre='" + store.getName() + '\'' +
            ", ubicacion='" + store.getLocation() + '\'' +
        "Administrator{" +
            "name='" + store.getAdministrator().getName() + '\'' +
            ", email='" + store.getAdministrator().getEmail() + '\'' +
            ", password='" + store.getAdministrator().getPassword() + '\'' +
            ", experiencia='" + store.getAdministrator().getExperience() + '\'' +
            ", sueldo='" + store.getAdministrator().getSalary() + '\'' +
            '}';
    }
}
