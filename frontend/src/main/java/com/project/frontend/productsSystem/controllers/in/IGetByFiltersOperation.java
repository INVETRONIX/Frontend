package com.project.frontend.productsSystem.controllers.in;

import java.io.IOException;
import java.util.List;
import com.project.frontend.productsSystem.models.Product;

public interface IGetByFiltersOperation {
    List<Product> findByFilters(String nameProduct, String category, String nameSupplier) throws IOException;
}