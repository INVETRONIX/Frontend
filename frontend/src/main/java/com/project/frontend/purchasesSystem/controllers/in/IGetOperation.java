package com.project.frontend.purchasesSystem.controllers.in;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.project.frontend.purchasesSystem.models.Purchase;

public interface IGetOperation {
    Purchase getPurchaseById(String id) throws IOException;
    List<Purchase> findByFilters(String nameClient, LocalDate date, LocalTime hour) throws IOException;
    List<Purchase> getAllPurchases() throws IOException;
}