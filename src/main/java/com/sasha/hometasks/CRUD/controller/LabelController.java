package com.sasha.hometasks.CRUD.controller;

import com.sasha.hometasks.CRUD.model.Label;
import com.sasha.hometasks.CRUD.repository.LabelRepository;
import com.sasha.hometasks.CRUD.repository.gson.GsonLabelRepositoryImpl;

import java.util.List;

public class LabelController {
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();
    public Label save(String name) {
        String checkedName = name.replaceAll("[^\\p{L}]", "");
        if(checkedName.isEmpty() || checkedName.isBlank() || checkedName.length() > 20)
            return null;

        Label label = new Label();
        label.setName(checkedName);

        List<Label> allLabels = getAll();
        if(allLabels.size() == 0)
            label.setId(1);
        else {
            int id = allLabels.get(allLabels.size() - 1).getId() + 1;
            label.setId(id);
        }

        labelRepository.save(label);

        return label;
    }

    public List<Label> getAll(){
        return labelRepository.getAll();
    }

    public Label getById(Integer id){
        if(id <= 0)
            return null;

        return labelRepository.getById(id);
    }

    public Label update(Label label){
        label.setName(label.getName().replaceAll("[^\\p{L}]", ""));
        if(label.getName().isEmpty() || label.getName().isBlank() || label.getName().length() > 20)
            return null;
        return labelRepository.update(label);
    }
    public boolean deleteById(Integer id){
        if(id <= 0)
            return false;

        return labelRepository.deleteById(id);
    }
}
