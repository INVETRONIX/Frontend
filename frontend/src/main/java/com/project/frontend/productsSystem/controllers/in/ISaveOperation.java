package com.project.frontend.productsSystem.controllers.in;

import java.io.IOException;
import com.project.frontend.productsSystem.models.Product;

public interface ISaveOperation {
    Product saveProduct(Product product) throws IOException;
}