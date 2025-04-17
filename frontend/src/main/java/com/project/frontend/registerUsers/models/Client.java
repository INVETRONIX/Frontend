package com.project.frontend.registerUsers.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Client{
    private String name;
    private String email;
    private String password;
    private String phone;
    private int age;

    public Client(String name, String email, String password, String phone, int age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.age = age;
    }

}