package com.project.frontend.returnsSystem.controllers.in;

import java.io.IOException;
import com.project.frontend.returnsSystem.models.Devolution;

public interface IUpdateOperation {
    Devolution updateDevolution(String id, Devolution devolution) throws IOException;
}