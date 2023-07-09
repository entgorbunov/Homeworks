package com.sasha.hometasks.CRUD.view;


import com.sasha.hometasks.CRUD.controller.PostController;
import com.sasha.hometasks.CRUD.model.Post;

import java.util.Scanner;

public class PostView {
    private final PostController postController = new PostController();
    private final Scanner reader = new Scanner(System.in);

    public void handleRequest() {
        showListOfCommands();

        String command;

        while (true) {
            command = reader.nextLine();
            switch (command) {
                case "c" -> createPost();
                case "r" -> getPostById();
                case "ra" -> getAllPosts();
                case "u" -> updatePost();
                case "d" -> deletePost();
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

    private void showListOfCommands() {
        System.out.printf("\n***Вы редактируете posts.json***\n\n" +
                "Список команд:\n" +
                "%-15s c\n" +
                "%-15s r\n" +
                "%-15s ra\n" +
                "%-15s u\n" +
                "%-15s d\n" +
                "%-15s help\n" +
                "%-15s exit\n", "добавить:", "получить:", "получить всех:", "изменить:", "удалить:", "список команд:", "главное меню:");
    }

    private int checkId() {
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

    public void createPost() {
        System.out.print("Напишите ваш текст: ");
        String content = reader.nextLine();

        Post post = postController.save(content);
        if (post == null)
            System.out.println("Неверный формат\n" +
                    "Убедитесь, что строка не пустая");
        else
            System.out.printf("Создан новый пост: %s\n", post);
    }

    public void getPostById() {
        int id = checkId();
        Post post = postController.getById(id);
        if (post == null)
            System.out.println("Пост с таким id не существует");
        else
            System.out.printf("%s\n", post);
    }

    public void getAllPosts() {
        for (Post s : PostController.getAll())
            System.out.println(s);
    }

    public void updatePost() {
        int id = checkId();
        Post post = postController.getById(id);
        if (post == null)
            System.out.println("Пост с таким id не существует");
        else {
            System.out.print("Обновите текст: ");
            String content = reader.nextLine();

            post.setContent(content);
            if (postController.update(post) == null)
                System.out.println("Неверный формат\n" +
                        "Убедитесь, что строка не пустая");
            else
                System.out.printf("Пост изменён: %s\n", post);
        }
    }

    public void deletePost() {
        int id = checkId();
        if (postController.deleteById(id))
            System.out.printf("Пост с id %d удалён\n", id);
        else
            System.out.println("Пост с таким id не существует");
    }
}

