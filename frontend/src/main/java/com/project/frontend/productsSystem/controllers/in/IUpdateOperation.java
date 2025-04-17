package com.project.frontend.productsSystem.controllers.in;

import java.io.IOException;
import com.project.frontend.productsSystem.models.Product;

public interface IUpdateOperation {
    Product updateProduct(String id, Product product) throws IOException;
}