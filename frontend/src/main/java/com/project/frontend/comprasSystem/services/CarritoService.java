package com.project.frontend.comprasSystem.services;

import com.project.frontend.comprasSystem.models.Carrito;
import java.io.*;

public class CarritoService {
    private static final String FILE_PATH = "carrito.ser";

    public static void guardarCarrito(Carrito carrito) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(carrito);
        }
    }

    public static Carrito cargarCarrito() throws IOException, ClassNotFoundException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new Carrito();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (Carrito) ois.readObject();
        }
    }
} 