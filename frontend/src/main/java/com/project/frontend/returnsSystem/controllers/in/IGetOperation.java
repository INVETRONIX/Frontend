package com.project.frontend.returnsSystem.controllers.in;

import java.io.IOException;
import java.util.List;
import com.project.frontend.returnsSystem.models.Devolution;

public interface IGetOperation {
    Devolution getDevolutionById(String id) throws IOException;
    List<Devolution> findByFilters(String nameClient, String date, String hour) throws IOException;
    List<Devolution> getAllDevolutions() throws IOException;
}