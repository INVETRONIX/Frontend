package com.project.frontend.registerUsers.models;

public class Administrator extends User {
    private String experience;
    private String salary;

    public Administrator(String name, String email, String password, String experience, String salary) {
        super(name, email, password);
        this.experience = experience;
        this.salary = salary;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
    
    public String toString(){
        return "Administrator{" +
        "experience='" + experience + '\'' +
        ", salary='" + salary +
        '}';
    }
}
