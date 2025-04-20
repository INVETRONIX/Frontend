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
}