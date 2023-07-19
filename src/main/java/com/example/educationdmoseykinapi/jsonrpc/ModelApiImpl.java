package com.example.educationdmoseykinapi.jsonrpc;

import com.example.educationdmoseykinapi.model.ModelRequest;
import com.example.educationdmoseykinapi.model.ModelResponse;
import com.example.educationdmoseykinapi.service.ModelService;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AutoJsonRpcServiceImpl
@RequiredArgsConstructor
public class ModelApiImpl implements ModelApi {

    private final ModelService modelService;

    @Override
    public ModelResponse save(ModelRequest modelRequest) {
        return modelService.save(modelRequest);
    }

    @Override
    public ModelResponse get(String id) {
        return modelService.get(id);
    }

    @Override
    public List<ModelResponse> getAll() {
        return modelService.getAll();
    }

    @Override
    public ModelResponse update(ModelRequest modelRequest) {
        return modelService.update(modelRequest);
    }

    @Override
    public void delete(String id) {
        modelService.delete(id);
    }
}
