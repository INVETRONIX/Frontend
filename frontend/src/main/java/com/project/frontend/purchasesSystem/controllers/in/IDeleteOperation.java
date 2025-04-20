package com.project.frontend.purchasesSystem.controllers.in;

import java.io.IOException;

public interface IDeleteOperation {
    boolean deletePurchase(String id) throws IOException;
}