package com.project.frontend.comprasSystem.services;

import com.project.frontend.comprasSystem.models.Carrito;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CarritoService implements Serializable {
    private static final String CARRO_FILE = "carrito.dat";
    private static CarritoService instance;
    private Carrito carrito;

    private CarritoService() {
        this.carrito = new Carrito();
        cargarCarrito();
    }

    public static CarritoService getInstance() {
        if (instance == null) {
            instance = new CarritoService();
        }
        return instance;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void guardarCarrito() {
        try (FileOutputStream file = new FileOutputStream(CARRO_FILE);
             ObjectOutputStream escritor = new ObjectOutputStream(file)) {
            escritor.writeObject(carrito);
        } catch (IOException ex) {
            System.err.println("Error al guardar el carrito: " + ex.getMessage());
        }
    }

    private void cargarCarrito() {
        try (FileInputStream file = new FileInputStream(CARRO_FILE);
             ObjectInputStream lector = new ObjectInputStream(file)) {
            carrito = (Carrito) lector.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Error al cargar el carrito: " + ex.getMessage());
            carrito = new Carrito();
        }
    }
} 