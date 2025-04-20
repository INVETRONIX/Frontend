package com.project.frontend.purchasesSystem.controllers.in;

import java.io.IOException;
import com.project.frontend.purchasesSystem.models.Purchase;

public interface IUpdateOperation {
    Purchase updatePurchase(String id, Purchase purchase) throws IOException;
}