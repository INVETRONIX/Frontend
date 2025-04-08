package com.project.frontend.productsSystem.models;

import com.project.frontend.suppliersSystem.models.Supplier;

public class Product {
    private String name;
    private String description;
    private double price;
    private String category;
    private int stockQuantity;
    private Supplier supplier;

    
    public Product() {
    }

    public Product(String name, String description, double price, String category, int quantityStock,
            Supplier supplier) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = quantityStock;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int quantityStock) {
        this.stockQuantity = quantityStock;
    }

    public Supplier GetSupplier(){
        return supplier;
    }

    public void setIdSupplier(Supplier supplier){
        this.supplier = supplier;
    }

   
}
