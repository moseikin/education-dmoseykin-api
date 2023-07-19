package com.example.educationdmoseykinapi.service;

import com.example.educationdmoseykinapi.model.ModelRequest;
import com.example.educationdmoseykinapi.model.ModelResponse;

import java.util.List;

public interface ModelService {

    ModelResponse save(ModelRequest modelRequest);

    ModelResponse get(String id);

    List<ModelResponse> getAll();

    ModelResponse update(ModelRequest modelRequest);

    void delete(String id);
}
