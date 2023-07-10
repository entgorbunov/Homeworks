package com.sasha.hometasks.CRUD.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sasha.hometasks.CRUD.model.Label;
import com.sasha.hometasks.CRUD.model.PostStatus;
import com.sasha.hometasks.CRUD.repository.LabelStorage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class GsonLabelStorageClass implements LabelStorage {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String LABEL_FILE_PATH = "src/main/java/com/sasha/hometasks/CRUD/labels.json";
    private AtomicInteger nextId = new AtomicInteger(1);

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

    private void writeToFile(List<Label> allLabels) {
        try (FileWriter fileWriter = new FileWriter(LABEL_FILE_PATH)) {
            fileWriter.write(GSON.toJson(allLabels));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getNextId() {
        List<Label> allLabels = getAllInternal();
        if (!allLabels.isEmpty()) {
            int maxId = allLabels.stream().mapToInt(Label::getId).max().orElse(0);
            nextId.set(maxId + 1);
        }
        return nextId.getAndIncrement();
    }

    @Override
    public Label save(Label label) {
        label.setId(getNextId());
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
        Optional<Label> optionalLabel = allLabels.stream()
                .filter(s -> s.getId().equals(label.getId()))
                .filter(s -> s.getStatus() == PostStatus.ACTIVE)
                .findFirst();

        optionalLabel.ifPresent(l -> {
            l.setName(label.getName());
            writeToFile(allLabels);
        });

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
        Optional<Label> optionalLabel = allLabels.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();

        if (optionalLabel.isPresent()) {
            Label label = optionalLabel.get();
            label.setStatus(PostStatus.DELETED);
            allLabels.remove(label);
            writeToFile(allLabels);
            return true;
        }
        return false;
    }
}
