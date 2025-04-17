package com.project.frontend.productsSystem.controllers.in;

import java.io.IOException;
import com.project.frontend.productsSystem.models.Product;

public interface IGetOperation {
    Product getProductById(String id) throws IOException;
}