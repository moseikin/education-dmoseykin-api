package com.example.educationdmoseykinapi.service;

import com.example.educationdmoseykinapi.exception.EntryNotFoundException;
import com.example.educationdmoseykinapi.model.Model;
import com.example.educationdmoseykinapi.model.ModelRequest;
import com.example.educationdmoseykinapi.model.ModelResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ModelServiceTest {
    public static final String NOT_EXISTED_ID = "-1";
    private static final String USER_NAME = "anonim";

    @Autowired
    private ModelService modelService;
    @Autowired
    private MongoOperations mongoOperations;

    @Test
    void save() {
        ModelRequest modelRequest = new ModelRequest(null, "it`s name", "i am comment");

        ModelResponse actualModelResponse = modelService.save(modelRequest);

        assertThat(actualModelResponse.getId()).isNotNull();
        assertThat(actualModelResponse.getName()).isEqualTo(modelRequest.getName());
        assertThat(actualModelResponse.getComment()).isEqualTo(modelRequest.getComment());
        assertThat(actualModelResponse.getCreateAt()).isNotNull();
        assertThat(actualModelResponse.getUpdateAt()).isNotNull();
        assertThat(actualModelResponse.getCreator()).isEqualTo(USER_NAME);
        assertThat(actualModelResponse.getUpdater()).isEqualTo(USER_NAME);
        assertThat(actualModelResponse.getCreateAt()).isEqualTo(actualModelResponse.getUpdateAt());
    }

    @Test
    void save_ShouldThrowIllegalStateException() {
        ModelRequest modelRequest = new ModelRequest("1", "it`s name", "i am comment");
        assertThrows(IllegalStateException.class, () -> modelService.save(modelRequest));
    }

    @Test
    void get() {
        Model model = new Model(null, "model name", "model comment", null, null, USER_NAME, USER_NAME);
        Model savedModel = mongoOperations.save(model);

        ModelResponse actualModelResponse = modelService.get(savedModel.getId());

        assertThat(actualModelResponse).usingRecursiveComparison().isEqualTo(savedModel);
    }

    @Test
    void get_ShouldThrowEntryNotFoundException() {
        assertThrows(EntryNotFoundException.class, () -> modelService.get(NOT_EXISTED_ID));
    }

    @Test
    void getAll() {
        mongoOperations.dropCollection("test");
        Model model1 = new Model(null, "name1", "comment1", null, null, USER_NAME, USER_NAME);
        Model model2 = new Model(null, "name2", "comment2", null, null, USER_NAME, USER_NAME);
        Model model3 = new Model(null, "name3", "comment3", null, null, USER_NAME, USER_NAME);
        mongoOperations.save(model1);
        mongoOperations.save(model2);
        mongoOperations.save(model3);
        List<Model> testModels = mongoOperations.findAll(Model.class);
        int expectedCount = testModels.size();
        assertThat(testModels).hasSize(expectedCount);

        int actualCount = modelService.getAll().size();

        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @Test
    void update() {
        Model model = new Model(null, "model name", "model comment", null, null, USER_NAME, USER_NAME);
        Model modelToUpdate = mongoOperations.save(model);

        ModelRequest modelRequest = new ModelRequest(modelToUpdate.getId(), "updated name", "updated comment");
        ModelResponse actualResponse = modelService.update(modelRequest);

        assertThat(actualResponse.getId()).isEqualTo(modelRequest.getId());
        assertThat(actualResponse.getName()).isEqualTo(modelRequest.getName());
        assertThat(actualResponse.getComment()).isEqualTo(modelRequest.getComment());
        assertThat(actualResponse.getCreateAt()).isEqualTo(modelToUpdate.getCreateAt());
        assertThat(actualResponse.getUpdateAt()).isAfter(modelToUpdate.getUpdateAt());
        assertThat(actualResponse.getCreator()).isEqualTo(modelToUpdate.getCreator());
        assertThat(actualResponse.getUpdater()).isEqualTo(modelToUpdate.getUpdater());
    }

    @Test
    void update_ShouldThrowIllegalStateException() {
        ModelRequest modelRequest = new ModelRequest(null, "it`s name", "i am comment");
        assertThrows(IllegalStateException.class, () -> modelService.update(modelRequest));
    }

    @Test
    void delete() {
        Model model = new Model(null, "model name", "model comment", null, null, USER_NAME, USER_NAME);
        Model modelToDelete = mongoOperations.save(model);
        assertThat(mongoOperations.findById(modelToDelete.getId(), Model.class)).isNotNull();

        modelService.delete(modelToDelete.getId());

        assertThat(mongoOperations.findById(modelToDelete.getId(), Model.class)).isNull();
    }

    @Test
    void delete_ShouldThrowEntryNotFoundException() {
        assertThrows(EntryNotFoundException.class, () -> modelService.delete(NOT_EXISTED_ID));
    }
}
