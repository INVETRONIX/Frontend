package com.project.frontend.purchasesSystem.controllers.in;

import java.io.IOException;
import com.project.frontend.purchasesSystem.models.Purchase;

public interface ISaveOperation {
    Purchase savePurchase(Purchase purchase) throws IOException;
}