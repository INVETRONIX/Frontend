package com.project.frontend.purchasesSystem.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.project.frontend.productsSystem.models.Product;
import com.project.frontend.registerUsers.models.Client;

public class Purchase {
    private Client client;
    private LocalTime hour;
    private LocalDate date;
    private List<Product> products;
    private double total;

    public Purchase() {
    }

    public Purchase(Client client, LocalTime hour, LocalDate date, List<Product> products, double total) {
        this.client = client;
        this.hour = hour;
        this.date = date;
        this.products = products;
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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