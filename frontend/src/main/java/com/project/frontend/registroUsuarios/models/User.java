package com.project.frontend.registroUsuarios.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class User {
    protected String name;
    protected String email;
    protected String password;

    public User(String name, String email, String password){
        this.name = name;
        this.email= email;
        this.password = password;
    }
    

    public String toString() {
        return "User{" +
        "name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        '}';
    }
}
