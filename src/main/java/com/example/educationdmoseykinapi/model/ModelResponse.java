package com.example.educationdmoseykinapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ModelResponse {

    private final String id;

    private final String name;

    private final String comment;

    private final String createAt;

    private final String updateAt;

    private final String creator;

    private final String updater;
}
