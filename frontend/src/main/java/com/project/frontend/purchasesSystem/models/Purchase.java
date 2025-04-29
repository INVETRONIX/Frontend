package com.project.frontend.purchasesSystem.models;

import java.util.List;

import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.registerUsers.models.Client;

public class Purchase {
    private String id;
    private Client client;
    private String hour;
    private String date;
    private List<Product> products;
    private double total;

    public Purchase() {
    }

    public Purchase(String id ,Client client, String hour, String date, List<Product> products, double total) {
        this.id = id;
        this.client = client;
        this.hour = hour;
        this.date = date;
        this.products = products;
        this.total = total;
    }

    public Purchase(Client client, String hour, String date, List<Product> products, double total) {
        this.client = client;
        this.hour = hour;
        this.date = date;
        this.products = products;
        this.total = total;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}