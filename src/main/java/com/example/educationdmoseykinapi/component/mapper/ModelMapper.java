package com.example.educationdmoseykinapi.component.mapper;

import com.example.educationdmoseykinapi.model.Model;
import com.example.educationdmoseykinapi.model.ModelRequest;
import com.example.educationdmoseykinapi.model.ModelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModelMapper {

    Model toModel(ModelRequest modelRequest);

    ModelResponse toModelResponse(Model model);
}
