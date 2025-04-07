package com.project.frontend.shared;

import java.util.List;

import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.registerUsers.models.Administrator;

public class Store {
    private String nit;
    private List<Product> products;
    private String name;
    private String location;
    private Administrator administrator;

    public Store(String nit, List<Product> products, String name, String location, Administrator administrator) {
        this.nit = nit;
        this.products = products;
        this.name = name;
        this.location = location;
        this.administrator = administrator;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProductos(List<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public String toString(){
        return "Store{" +
        "nit='" + nit + '\'' +
        ", products='" + products + '\'' +
        ", name='" + name + '\'' +
        ", location='" + location + '\'' +
            "Administrator{" +
            "name='" + administrator.getName() + '\'' +
            ", email='" + administrator.getEmail() + '\'' +
            ", password='" + administrator.getPassword() + '\'' +
            ", experiencia='" + administrator.getExperience() + '\'' +
            ", sueldo='" + administrator.getSalary() +
            '}';
    }
}
