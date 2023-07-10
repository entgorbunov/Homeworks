package com.sasha.hometasks.CRUD.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sasha.hometasks.CRUD.model.PostStatus;
import com.sasha.hometasks.CRUD.model.Writer;
import com.sasha.hometasks.CRUD.repository.WriterStorage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GsonWriterStorageClass implements WriterStorage {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private final String WRITERS_FILE_PATH = "src/main/java/com/sasha/hometasks/CRUD/writers.json";
    private AtomicInteger nextId = new AtomicInteger(1);

    private int getNextId() {
        List<Writer> allWriters = getAllInternal();
        if (!allWriters.isEmpty()) {
            int maxId = allWriters.stream().mapToInt(Writer::getId).max().orElse(0);
            nextId.set(maxId + 1);
        }
        return nextId.getAndIncrement();
    }

    @Override
    public Writer save(Writer writer) {
        writer.setId(getNextId());
        List<Writer> allWriters = getAllInternal();
        try (FileWriter filewriter = new FileWriter(WRITERS_FILE_PATH)) {
            allWriters.add(writer);
            filewriter.write(GSON.toJson(allWriters));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }


    @Override
    public Writer update(Writer writer) {
        List<Writer> allWriters = getAllInternal();
        allWriters.stream()
                .filter(w -> w.getId().equals(writer.getId()) && w.getStatus() == PostStatus.ACTIVE)
                .findFirst()
                .ifPresent(w -> {
                    int index = allWriters.indexOf(w);
                    allWriters.set(index, writer);
                });
        writeToFile(allWriters);
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> allWriters = getAllInternal();
        return allWriters.stream()
                .filter(writer -> writer.getStatus() == PostStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public Writer getById(Integer id) {
        List<Writer> allWriters = getAllInternal();
        return allWriters.stream()
                .filter(writer -> writer.getId().equals(id) && writer.getStatus() == PostStatus.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Writer> allWriters = getAllInternal();
        AtomicBoolean isDeleted = new AtomicBoolean(false);
        allWriters.stream()
                .filter(writer -> writer.getId().equals(id) && writer.getStatus() == PostStatus.ACTIVE)
                .findFirst()
                .ifPresent(writer -> {
                    writer.setStatus(PostStatus.DELETED);
                    allWriters.remove(writer);
                    isDeleted.set(false);
                });
        if (isDeleted.get()) {
            writeToFile(allWriters);
        }
        return isDeleted.get();
    }

    private List<Writer> getAllInternal() {
        List<Writer> allWriters;
        try (FileReader reader = new FileReader(WRITERS_FILE_PATH)) {
            Type targetClassType = new TypeToken<ArrayList<Writer>>() {
            }.getType();
            allWriters = GSON.fromJson(reader, targetClassType);
            if (allWriters == null)
                allWriters = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allWriters;
    }

    private void writeToFile(List<Writer> allWriters) {
        try (FileWriter fileWriter = new FileWriter(WRITERS_FILE_PATH)) {
            fileWriter.write(GSON.toJson(allWriters));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
