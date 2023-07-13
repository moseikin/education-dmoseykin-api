package com.example.educationdmoseykinapi.repository;

import com.example.educationdmoseykinapi.model.Model;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends MongoRepository<Model, String> {
}
