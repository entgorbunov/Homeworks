package com.sasha.hometasks.CRUD.view;



import com.sasha.hometasks.CRUD.controller.LabelController;
import com.sasha.hometasks.CRUD.model.Label;

import java.util.Scanner;

public class LabelView {
    private final LabelController labelController = new LabelController();
    private final Scanner reader = new Scanner(System.in);

    public void handleRequest(){
        showListOfCommands();

        String command;

        while(true){
            command = reader.nextLine();
            switch (command) {
                case "c" -> createLabel();
                case "r" -> getLabelById();
                case "ra" -> getAllLabels();
                case "u" -> updateLabel();
                case "d" -> deleteLabel();
                case "help" -> showListOfCommands();
                case "exit" -> {
                    System.out.println("Выход в главное меню");
                    return;
                }
                default -> System.out.println("Вы ввели неверную команду\n" +
                        "список команд: -help");
            }
        }
    }

    private void showListOfCommands(){
        System.out.printf("\n***Вы в редакторе labels.json***\n\n" +
                "Список команд:\n" +
                "%-15s c\n" +
                "%-15s r\n" +
                "%-15s ra\n" +
                "%-15s u\n" +
                "%-15s d\n" +
                "%-15s help\n" +
                "%-15s exit\n", "добавить:", "получить:", "получить всех:", "изменить:", "удалить:", "список команд:", "главное меню:");
    }

    private int checkId(){
        int id;
        while (true) {
            System.out.print("Введите id: ");
            if (reader.hasNextInt()) {
                id = reader.nextInt();
                break;
            } else {
                System.out.println("id не может содержать буквы или символы");
                reader.nextLine();
            }
        }
        reader.nextLine();

        return id;
    }

    public void createLabel(){
        System.out.print("Введите название: ");
        String name = reader.nextLine();

        Label label = labelController.save(name);
        if(label == null)
            System.out.println("Неверный формат\n" +
                    "Убедитесь, что строка не пустая и не превышает 10 символов");
        else
            System.out.printf("Создан новый лейбл: %s\n", label);
    }

    public void getLabelById(){
        int id = checkId();
        Label label = labelController.getById(id);
        if(label == null)
            System.out.println("Лейбл с таким id не существует");
        else
            System.out.printf("%s\n", label);

    }

    public void getAllLabels(){
        for(Label s: labelController.getAll())
            System.out.println(s);
    }

    public void updateLabel(){
        int id = checkId();
        Label label = labelController.getById(id);
        if(label == null)
            System.out.println("Пост с таким id не существует");
        else {
            System.out.print("Введите новое название: ");
            String name = reader.nextLine();

            label.setName(name);
            if(labelController.update(label) == null)
                System.out.println("Неверный формат\n" +
                        "Убедитесь, что строка не пустая и не превышает 10 символов");
            else
                System.out.printf("Лейбл изменен: %s\n", label);
        }
    }

    public void deleteLabel(){
        int id = checkId();
        if(labelController.deleteById(id))
            System.out.printf("Лейбл с id %d удален\n", id);
        else
            System.out.println("Лейбл с таким id не существует");
    }
}

