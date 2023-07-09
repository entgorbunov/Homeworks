package com.sasha.hometasks.CRUD.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sasha.hometasks.CRUD.model.PostStatus;
import com.sasha.hometasks.CRUD.model.Writer;
import com.sasha.hometasks.CRUD.repository.WriterRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonWriterRepositoryImpl implements WriterRepository {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private final String WRITERS_FILE_PATH = "src/main/java/com/sasha/hometasks/CRUD/writers.json";

    @Override
    public Writer save(Writer writer) {
        List<Writer> allWriters = getAllInternal();
        try (FileWriter filewriter = new FileWriter(WRITERS_FILE_PATH)){
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
        for (Writer w : allWriters) {
            if (w.getId().equals(writer.getId()) && w.getStatus() == PostStatus.ACTIVE) {
                int index = allWriters.indexOf(w);
                allWriters.set(index, writer);
                break;
            }
        }
        writeToFile(allWriters);
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> activeWriters = new ArrayList<>();
        List<Writer> allWriters = getAllInternal();
        for (Writer writer : allWriters) {
            if (writer.getStatus() == PostStatus.ACTIVE) {
                activeWriters.add(writer);
            }
        }
        return activeWriters;
    }

    @Override
    public Writer getById(Integer id) {
        List<Writer> allWriters = getAllInternal();
        for (Writer writer : allWriters) {
            if (writer.getId().equals(id) && writer.getStatus() == PostStatus.ACTIVE) {
                return writer;
            }

        }
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Writer> allWriters = getAllInternal();
        for (Writer writer : allWriters) {
            if (writer.getId().equals(id) && writer.getStatus() == PostStatus.ACTIVE) {
                writer.setStatus(PostStatus.DELETED);
                writeToFile(allWriters);
                return true;
            }
        }
        return false;
    }
    private List<Writer> getAllInternal() {
        List<Writer> allWriters;
        try(FileReader reader = new FileReader(WRITERS_FILE_PATH)){
            Type targetClassType = new TypeToken<ArrayList<Writer>>() { }.getType();
            allWriters = GSON.fromJson(reader, targetClassType);
            if(allWriters == null)
                allWriters = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allWriters;
    }

    private void writeToFile(List<Writer> allWriters){
        try(FileWriter fileWriter = new FileWriter(WRITERS_FILE_PATH)){
            fileWriter.write(GSON.toJson(allWriters));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
