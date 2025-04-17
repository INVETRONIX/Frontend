package com.project.frontend.productsSystem.controllers.in;

import java.io.IOException;
import java.util.List;
import com.project.frontend.productsSystem.models.Product;

public interface IGetAllOperation {
     List<Product> getAllProducts() throws IOException;
}