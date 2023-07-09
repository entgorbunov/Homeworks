package com.sasha.hometasks.CRUD.controller;

import com.sasha.hometasks.CRUD.model.Writer;
import com.sasha.hometasks.CRUD.repository.WriterRepository;
import com.sasha.hometasks.CRUD.repository.gson.GsonWriterRepositoryImpl;

import java.util.List;

public class WriterController {
    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();

    public Writer save(String firstName, String lastName) {
        String checkedFirstName = firstName.replaceAll("[^\\p{L}]", "");
        String checkedLastName = lastName.replaceAll("[^\\p{L}]", "");
        if (checkedFirstName.isEmpty() || checkedLastName.isEmpty()) return null;
        if (checkedFirstName.length() > 10 || checkedLastName.length() > 10) return null;
        Writer writer = new Writer();
        writer.setFirstName(checkedFirstName);
        writer.setLastName(checkedLastName);
        List<Writer> allWriters = writerRepository.getAll();
        if (allWriters.size() == 0) {
            writer.setId(1);
        } else {
            int id = allWriters.get(allWriters.size() - 1).getId() + 1;
            writer.setId(id);
        }
        writerRepository.save(writer);
        return writer;
    }

    public Writer getById(Integer id) {
        if (id <= 0) {
            return null;
        }
        return writerRepository.getById(id);
    }

    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    public Writer update(Writer writer) {
        Writer outdateWriter = getById(writer.getId());
        if (!writer.getFirstName().equals(outdateWriter.getFirstName())) {
            String checkedFirstName = writer.getFirstName().replaceAll("[^\\p{L}]", "");
            writer.setFirstName(checkedFirstName);
            if (writer.getFirstName().isEmpty()) {
                return null;
            }
            if (writer.getFirstName().length() > 10) {
                return null;
            }
        }
        if (!writer.getLastName().equals(outdateWriter.getLastName())) {
            String checkedFirstName = writer.getLastName().replaceAll("[^\\p{L}]", "");
            writer.setLastName(checkedFirstName);
            if (writer.getLastName().isEmpty()) {
                return null;
            }
            if (writer.getLastName().length() > 10) {
                return null;
            }
        }
        return writerRepository.update(writer);
    }

    public boolean deleteById(Integer id) {
        if (id <= 0) {
            return false;
        }
        return writerRepository.deleteById(id);
    }
}
