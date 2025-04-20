package com.project.frontend.productsSystem.controllers.in;

import java.io.IOException;
import java.util.List;
import com.project.frontend.productsSystem.models.Product;

public interface IGetOperation {
    Product getProductById(String id) throws IOException;
    List<Product> findByFilters(String nameProduct, String category, String nameSupplier) throws IOException;
    List<Product> getAllProducts() throws IOException;
}