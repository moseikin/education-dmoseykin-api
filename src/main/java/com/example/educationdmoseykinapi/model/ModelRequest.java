package com.example.educationdmoseykinapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ModelRequest {

    private final String id;

    private final String name;

    private final String comment;
}
