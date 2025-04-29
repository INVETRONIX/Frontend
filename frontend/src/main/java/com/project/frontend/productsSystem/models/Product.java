package com.project.frontend.productsSystem.models;

import java.io.Serializable;

public class Product implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int stockQuantity;
    private String supplier;
    
    public Product() {
    }

    public Product(String id, String name, String description, double price, String category, int quantityStock,
    String supplier) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = quantityStock;
        this.supplier = supplier;
    }

    public Product(String name, String description, double price, String category, int stockQuantity, String supplier) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.supplier = supplier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
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

    public String GetSupplier(){
        return supplier;
    }

    public void setIdSupplier(String supplier){
        this.supplier = supplier;
    }

}