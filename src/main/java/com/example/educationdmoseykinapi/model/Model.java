package com.example.educationdmoseykinapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Model {

    private String id;

    private String name;

    private String comment;

    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private Date updateAt;

    private String creator;

    private String updater;
}
