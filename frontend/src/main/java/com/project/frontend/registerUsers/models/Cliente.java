package com.project.frontend.registerUsers.models;

public class Cliente extends User{
    private String phone;
    private int age;

    public Cliente (String name, String email, String password,String phone, int age){
        super(name, email, password);
        this.phone = phone;
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString(){
        return "Costumer{" +
        "phone='" + phone + '\'' +
        ", age='" + age +
        '}';
    }
}
