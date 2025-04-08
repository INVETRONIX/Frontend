package com.project.frontend.suppliersSystem.models;

public class Supplier {
    private String name;
    private String company;
    private String phone;
    private String email;

    public Supplier(String name, String company, String phone, String email){
        this.name = name;
        this.company = company;
        this.phone = phone;
        this.email = email;
    }


    public Supplier() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String store) {
        this.company = store;
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


}
