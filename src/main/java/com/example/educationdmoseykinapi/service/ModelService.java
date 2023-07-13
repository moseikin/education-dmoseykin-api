package com.example.educationdmoseykinapi.service;

import com.example.educationdmoseykinapi.component.mapper.ModelMapper;
import com.example.educationdmoseykinapi.exception.EntryNotFoundException;
import com.example.educationdmoseykinapi.jsonRPS.ModelApi;
import com.example.educationdmoseykinapi.model.Model;
import com.example.educationdmoseykinapi.model.ModelRequest;
import com.example.educationdmoseykinapi.model.ModelResponse;
import com.example.educationdmoseykinapi.repository.ModelRepository;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AutoJsonRpcServiceImpl
@RequiredArgsConstructor
public class ModelService implements ModelApi {

    public static final String ENTRY_NOT_FOUND = "Запись с id=%s не найдена";
    private static final String ID_NOT_PRESENT = "Отсутствует Id обновляемой записи";
    private static final String ID_MUST_BE_NULL = "Для новой записи Id не нужен";

    private final ModelMapper modelMapper;
    private final ModelRepository modelRepository;

    @Override
    public ModelResponse save(ModelRequest modelRequest) {
        checkIsIdAbsent(modelRequest.getId());

        Model model = modelMapper.toModel(modelRequest);
        // toDo Когда появится аутентификация, то над creator и updater поставить @CreatedBy и @LastModifiedBy, а сеттинг убрать
        model.setCreator("anonim");
        model.setUpdater("anonim");
        Model savedModel = modelRepository.save(model);
        return modelMapper.toModelResponse(savedModel);
    }

    private void checkIsIdAbsent(String id) {
        if (id != null) {
            throw new IllegalStateException(ID_MUST_BE_NULL);
        }
    }

    @Override
    public ModelResponse get(String id) {
        Model model = modelRepository.findById(id).orElseThrow(
                () -> new EntryNotFoundException(String.format(ENTRY_NOT_FOUND, id)));
        return modelMapper.toModelResponse(model);
    }

    @Override
    public List<ModelResponse> getAll() {
        List<Model> models = modelRepository.findAll();
        return models.stream().map(modelMapper::toModelResponse).collect(Collectors.toList());
    }

    @Override
    public ModelResponse update(ModelRequest modelRequest) {
        checkIsIdPresent(modelRequest.getId());

        Optional<Model> optionalModel = modelRepository.findById(modelRequest.getId());

        if (optionalModel.isEmpty()) {
            throw new EntryNotFoundException(String.format(ENTRY_NOT_FOUND, modelRequest.getId()));
        } else {
            Model model = updateFields(optionalModel.get(), modelRequest);
            modelRepository.save(model);
            return modelMapper.toModelResponse(model);
        }
    }

    private void checkIsIdPresent(String id) {
        if (id == null) {
            throw new IllegalStateException(ID_NOT_PRESENT);
        }
    }

    private Model updateFields(Model model, ModelRequest modelRequest) {
        model.setName(modelRequest.getName());
        model.setComment(modelRequest.getComment());
        // toDo Когда появится аутентификация, то над creator и updater поставить @CreatedBy и @LastModifiedBy, а сеттинг убрать
        model.setUpdater("anonim");
        return model;
    }

    @Override
    public void delete(String id) {
        boolean isEntryExists = modelRepository.existsById(id);

        if (isEntryExists) {
            modelRepository.deleteById(id);
        } else {
            throw new EntryNotFoundException(String.format(ENTRY_NOT_FOUND, id));
        }
    }
}
