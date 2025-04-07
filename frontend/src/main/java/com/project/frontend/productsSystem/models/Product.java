package com.project.frontend.productsSystem.models;

import com.project.frontend.suppliersSystem.models.Supplier;

public class Product {
    private String name;
    private String description;
    private double price;
    private String category;
    private int quantityStock;
    private Supplier supplier;
    
    public Product(String name, String description, double price, String category, int quantityStock,
            Supplier supplier) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantityStock = quantityStock;
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    public Supplier GetSupplier(){
        return supplier;
    }

    public void setIdSupplier(Supplier supplier){
        this.supplier = supplier;
    }

    public String toString(){
        return "Product{" +
        "name='" +  + '\'' +
        ", description='" + description + '\'' +
        ", price='" + price + '\'' +
        ", category='" + category + '\'' +
        ", quantityStock='" + quantityStock + '\'' +
        "Supplier{" +
            "id='" + supplier.getId() + '\'' +
            ", name='" + supplier.getName() + '\'' +
            "Store{" +
                "nit='" + supplier.getStore().getNit() + '\'' +
                ", products='" + supplier.getStore().getProducts() + '\'' +
                ", name='" + supplier.getStore().getName() + '\'' +
                ", location='" + supplier.getStore().getLocation() + '\'' +
                "Administrator{" +
                    "name='" + supplier.getStore().getAdministrator().getName() + '\'' +
                    ", email='" + supplier.getStore().getAdministrator().getEmail() + '\'' +
                    ", password='" + supplier.getStore().getAdministrator().getPassword() + '\'' +
                    ", experiencia='" + supplier.getStore().getAdministrator().getExperience() + '\'' +
                    ", sueldo='" + supplier.getStore().getAdministrator().getSalary() + '\'' +
        '}';
    }
}
