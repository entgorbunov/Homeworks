package com.sasha.hometasks.CRUD.controller;

import com.sasha.hometasks.CRUD.model.Label;
import com.sasha.hometasks.CRUD.repository.LabelStorage;
import com.sasha.hometasks.CRUD.repository.gson.GsonLabelStorageClass;

import java.util.List;

public class LabelController {
    private final LabelStorage labelStorage = new GsonLabelStorageClass();
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

        labelStorage.save(label);

        return label;
    }

    public List<Label> getAll(){
        return labelStorage.getAll();
    }

    public Label getById(Integer id){
        if(id <= 0)
            return null;

        return labelStorage.getById(id);
    }

    public Label update(Label label){
        label.setName(label.getName().replaceAll("[^\\p{L}]", ""));
        if(label.getName().isEmpty() || label.getName().isBlank() || label.getName().length() > 20)
            return null;
        return labelStorage.update(label);
    }
    public boolean deleteById(Integer id){
        if(id <= 0)
            return false;

        return labelStorage.deleteById(id);
    }
}
