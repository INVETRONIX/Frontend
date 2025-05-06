package com.project.frontend.comprasSystem.services;

import com.project.frontend.comprasSystem.models.Producto;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class ProductoService {
    private final String API_URL = "http://localhost:8080/api/productos";
    private final RestTemplate restTemplate;

    public ProductoService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Producto> getAllProductos() {
        Producto[] productos = restTemplate.getForObject(API_URL, Producto[].class);
        return Arrays.asList(productos);
    }
} 