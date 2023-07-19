package com.example.educationdmoseykinapi.service;

import com.example.educationdmoseykinapi.component.mapper.ModelMapper;
import com.example.educationdmoseykinapi.exception.EntryNotFoundException;
import com.example.educationdmoseykinapi.model.Model;
import com.example.educationdmoseykinapi.model.ModelRequest;
import com.example.educationdmoseykinapi.model.ModelResponse;
import com.example.educationdmoseykinapi.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ModelServiceImpl implements ModelService {

    public static final String ENTRY_NOT_FOUND = "Запись с id=%s не найдена";
    private static final String ID_NOT_PRESENT = "Отсутствует Id обновляемой записи";
    public static final String DEFAULT_USERNAME = "anonim";

    private final ModelMapper modelMapper;
    private final ModelRepository modelRepository;
    private final DateConverterService dateConverterService;

    @Override
    @Transactional
    public ModelResponse save(ModelRequest modelRequest) {
        Model model = modelMapper.toModel(modelRequest);
        setAuditingFields(model);

        Model savedModel = modelRepository.save(model);
        return modelMapper.toModelResponse(savedModel, dateConverterService);
    }

    private void setAuditingFields(Model model) {
        model.setCreator(DEFAULT_USERNAME);
        model.setUpdater(DEFAULT_USERNAME);
    }

    @Override
    public ModelResponse get(String id) {
        Model model = modelRepository.findById(id).orElseThrow(
                () -> new EntryNotFoundException(String.format(ENTRY_NOT_FOUND, id)));
        return modelMapper.toModelResponse(model, dateConverterService);
    }

    @Override
    public List<ModelResponse> getAll() {
        List<Model> models = modelRepository.findAll();
        return models.stream().map(model -> modelMapper.toModelResponse(model, dateConverterService))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ModelResponse update(ModelRequest modelRequest) {
        checkIsIdPresent(modelRequest.getId());

        Optional<Model> optionalModel = modelRepository.findById(modelRequest.getId());

        if (optionalModel.isEmpty()) {
            throw new EntryNotFoundException(String.format(ENTRY_NOT_FOUND, modelRequest.getId()));
        } else {
            Model model = modelMapper.updateModel(optionalModel.get(), modelRequest);
            modelRepository.save(model);
            return modelMapper.toModelResponse(model, dateConverterService);
        }
    }

    private void checkIsIdPresent(String id) {
        if (id == null) {
            throw new IllegalStateException(ID_NOT_PRESENT);
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        boolean isEntryExists = modelRepository.existsById(id);

        if (isEntryExists) {
            modelRepository.deleteById(id);
        } else {
            throw new EntryNotFoundException(String.format(ENTRY_NOT_FOUND, id));
        }
    }
}
