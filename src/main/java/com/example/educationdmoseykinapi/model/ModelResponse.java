package com.example.educationdmoseykinapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class ModelResponse {

    private final String id;

    private final String name;

    private final String comment;

    private final Date createAt;

    private final Date updateAt;

    private final String creator;

    private final String updater;
}
