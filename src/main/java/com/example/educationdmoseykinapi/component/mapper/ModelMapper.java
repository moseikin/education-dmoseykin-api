package com.example.educationdmoseykinapi.component.mapper;

import com.example.educationdmoseykinapi.model.Model;
import com.example.educationdmoseykinapi.model.ModelRequest;
import com.example.educationdmoseykinapi.model.ModelResponse;
import com.example.educationdmoseykinapi.service.DateConverterService;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModelMapper {

    Model toModel(ModelRequest modelRequest);

    @Mapping(expression = "java(dateConverterService.convert(model.getCreateAt()))", target = "createAt")
    @Mapping(expression = "java(dateConverterService.convert(model.getUpdateAt()))", target = "updateAt")
    ModelResponse toModelResponse(Model model, @Context DateConverterService dateConverterService);

    Model updateModel(@MappingTarget Model model, ModelRequest modelRequest);
}
