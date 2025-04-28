package com.project.frontend.view.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.project.frontend.productsSystem.models.Product;

public class Data {
    private static Data instance;

    private Data(){}

    public static Data getInstance(){
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public void write(List<Product> db) {
        try{
            FileOutputStream file = new FileOutputStream("carrito.dat");
            ObjectOutputStream escritor = new ObjectOutputStream(file);
            escritor.writeObject(db);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public List<Product> read() {
        try {
            FileInputStream file = new FileInputStream("carrito.dat");
            ObjectInputStream lector = new ObjectInputStream(file);
            return (List<Product>) lector.readObject();
        }  catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
}
