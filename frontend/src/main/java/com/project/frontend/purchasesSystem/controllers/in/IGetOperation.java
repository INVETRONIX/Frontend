package com.project.frontend.purchasesSystem.controllers.in;

import java.io.IOException;
import java.util.List;
import com.project.frontend.purchasesSystem.models.Purchase;

public interface IGetOperation {
    Purchase getPurchaseById(String id) throws IOException;
    List<Purchase> findByFilters(String nameClient, String date, String hour) throws IOException;
    List<Purchase> getAllPurchases() throws IOException;
}