package com.example.educationdmoseykinapi.jsonRPS;

import com.example.educationdmoseykinapi.model.ModelRequest;
import com.example.educationdmoseykinapi.model.ModelResponse;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

import java.util.List;

@JsonRpcService("/model")
public interface ModelApi {

    ModelResponse save(@JsonRpcParam(value = "request") ModelRequest modelRequest);

    ModelResponse get(@JsonRpcParam(value = "id") String id);

    List<ModelResponse> getAll();

    ModelResponse update(@JsonRpcParam(value = "request") ModelRequest modelRequest);

    void delete(@JsonRpcParam(value = "id") String id);
}
