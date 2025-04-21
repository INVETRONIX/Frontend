package com.project.frontend.returnsSystem.controllers.in;

import java.io.IOException;
import com.project.frontend.returnsSystem.models.Devolution;

public interface ISaveOperation {
    Devolution saveDevolution(Devolution devolution) throws IOException;
}