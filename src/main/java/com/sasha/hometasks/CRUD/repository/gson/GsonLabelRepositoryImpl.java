package com.sasha.hometasks.CRUD.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sasha.hometasks.CRUD.model.Label;
import com.sasha.hometasks.CRUD.model.PostStatus;
import com.sasha.hometasks.CRUD.repository.LabelRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonLabelRepositoryImpl implements LabelRepository {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String LABEL_FILE_PATH = "src/main/java/com/sasha/hometasks/CRUD/labels.json";

    private List<Label> getAllInternal() {
        List<Label> allLabels;
        try (FileReader fileReader = new FileReader(LABEL_FILE_PATH)) {
            Type targetClassType = new TypeToken<ArrayList<Label>>() {
            }.getType();
            allLabels = GSON.fromJson(fileReader, targetClassType);
            if (allLabels == null)
                allLabels = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allLabels;
    }

    private void writeToFile(List<Label> allLables) {
        try (FileWriter fileWriter = new FileWriter(LABEL_FILE_PATH)) {
            fileWriter.write(GSON.toJson(allLables));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Label save(Label label) {
        List<Label> allLabels = getAllInternal();
        try (FileWriter fileWriter = new FileWriter(LABEL_FILE_PATH)) {
            allLabels.add(label);
            fileWriter.write(GSON.toJson(allLabels));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return label;
    }

    @Override
    public Label update(Label label) {
        List<Label> allLabels = getAllInternal();
        for (Label l : allLabels) {
            if (l.getId().equals(label.getId()) && l.getStatus() == PostStatus.ACTIVE) {
                l.setName(label.getName());
            }
        }
        writeToFile(allLabels);
        return label;
    }

    @Override
    public List<Label> getAll() {
        return getAllInternal();
    }

    @Override
    public Label getById(Integer id) {
        List<Label> allLabels = getAllInternal();
        for (Label label : allLabels) {
            if (label.getId().equals(id) && label.getStatus() == PostStatus.ACTIVE) {
                return label;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Label> allLabels = getAllInternal();
        boolean isDeleted = false;
        for (Label label : allLabels) {
            if (label.getId().equals(id)) {
                label.setStatus(PostStatus.DELETED);
                allLabels.remove(label);
                isDeleted = true;
                break;
            }
        }
        if (isDeleted) {
            writeToFile(allLabels);
        }
        return isDeleted;
    }
}
