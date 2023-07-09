package com.sasha.hometasks.CRUD.view;

import com.sasha.hometasks.CRUD.controller.LabelController;
import com.sasha.hometasks.CRUD.controller.PostController;
import com.sasha.hometasks.CRUD.controller.WriterController;
import com.sasha.hometasks.CRUD.model.Label;
import com.sasha.hometasks.CRUD.model.Post;
import com.sasha.hometasks.CRUD.model.Writer;


import java.util.Scanner;

public class WriterView {
    private final WriterController writerController = new WriterController();
    private final PostController postController = new PostController();
    private final LabelController labelController = new LabelController();
    private final Scanner reader = new Scanner(System.in);

    public void handleRequest(){
        showListOfCommands();
        String command;
        while(true){
            command = reader.nextLine();
            switch (command) {
                case "-c" -> createWriter();
                case "-r" -> getWriterById();
                case "-ra" -> getAllWriters();
                case "-u" -> updateWriter();
                case "-d" -> deleteWriter();
                case "-help" -> showListOfCommands();
                case "-exit" -> {
                    System.out.println("Выход в главное меню");
                    return;
                }
                default -> System.out.println("Вы ввели неверную команду\n" +
                        "список команд: -help");
            }
        }
    }
    private void showListOfCommands(){
        System.out.printf("\n***Вы в редакторе хранилища writers.json***\n\n" +
                "Список команд:\n" +
                "%-15s -c\n" +
                "%-15s -r\n" +
                "%-15s -ra\n" +
                "%-15s -u\n" +
                "%-15s -d\n" +
                "%-15s -help\n" +
                "%-15s -exit\n", "добавить:", "получить:", "получить всех:", "изменить:", "удалить:", "список команд:", "главное меню:");
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

    private void createWriter(){
        System.out.print("Введите имя: ");
        String firstName = reader.nextLine();

        System.out.print("Введите фамилию: ");
        String lastName = reader.nextLine();

        Writer writer = writerController.save(firstName, lastName);
        if(writer == null)
            System.out.println("Неверный формат\n" +
                    "Возможно, строка пустая или превышает 10 символов, или введены цифры");
        else
            System.out.printf("Создан новый писатель: %s\n", writer);
    }

    public void getWriterById(){
        int id = checkId();
        Writer writer = writerController.getById(id);
        if(writer == null)
            System.out.println("Писатель с таким id не существует");
        else
            System.out.printf("%s\n", writer);
    }

    public void getAllWriters(){
        for(Writer d: writerController.getAll())
            System.out.println(d);
    }

    public void updateWriter(){
        int id = checkId();
        Writer writer = writerController.getById(id);
        if(writer == null)
            System.out.println("Писатель с таким id не существует");
        else{
            System.out.println("Введите нужное поле для редактирования:\n" +
                    " first name \n" +
                    " last name \n" +
                    " post \n" +
                    " label ");

            String fieldName = reader.nextLine();
            switch (fieldName) {
                case "first name" -> {
                    System.out.print("Введите имя: ");
                    String firstName = reader.nextLine();
                    writer.setFirstName(firstName);
                    if (writerController.update(writer) == null)
                        System.out.println("Неверный формат\n" +
                                "Убедитесь, что строка не пустая и не превышает 10 символов");
                    else
                        System.out.printf("Имя писателя изменено: %s\n", writer);
                }
                case "last name" -> {
                    System.out.print("Введите фамилию: ");
                    String lastName = reader.nextLine();
                    writer.setLastName(lastName);
                    if (writerController.update(writer) == null)
                        System.out.println("Неверный формат\n" +
                                "Убедитесь, что строка не пустая и не превышает 10 символов");
                    else
                        System.out.printf("Фамилия писателя изменена: %s\n", writer);
                }
                case "post" -> {
                    int postId = checkId();
                    Post post = postController.getById(postId);
                    if (post == null)
                        System.out.println("Пост с таким id не существует");
                    else {
                        post.setId(id);
                        postController.update(post);
                        System.out.printf("Посты этого писателя изменены: %s\n", post);
                    }
                }
                case "label" -> {
                    int labelId = checkId();
                    Label label = labelController.getById(labelId);
                    if (label == null)
                        System.out.println("Лейбл с таким id не существует");
                    else {
                        label.setId(id);
                        labelController.update(label);
                        System.out.printf("Лейбл писателя изменен: %s\n", label);
                    }
                }
                default -> System.out.println("Такое поле не существует");
            }
        }
    }

    public void deleteWriter(){
        int id = checkId();
        if(writerController.deleteById(id))
            System.out.printf("Разработчик с id %d удалён\n", id);
        else
            System.out.println("Разработчик с таким id не существует");
    }
}
