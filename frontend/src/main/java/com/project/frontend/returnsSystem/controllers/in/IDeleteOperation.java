package com.project.frontend.returnsSystem.controllers.in;

import java.io.IOException;

public interface IDeleteOperation {
    boolean deleteProduct(String id) throws IOException;
}