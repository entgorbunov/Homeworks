package com.sasha.hometasks.CRUD.view;


import com.sasha.hometasks.CRUD.model.Writer;

import java.util.Scanner;

public class MainView {
    private final WriterView writerView = new WriterView();
    private final PostView postView = new PostView();
    private final LabelView labelView = new LabelView();
    private final Scanner reader = new Scanner(System.in);

    public void handleStartRequest() {
        showListOfCommands();

        String command;

        while (true) {
            command = reader.nextLine();

            switch (command) {
                case "1":
                    writerView.handleRequest();
                    break;
                case "2":
                    postView.handleRequest();
                    break;
                case "3":
                    labelView.handleRequest();
                    break;
                case "-help":
                    showListOfCommands();
                    break;
                case "-exit":
                    System.out.println("Завершение программы");
                    return;
                default:
                    System.out.println("Вы ввели неверную команду\n" +
                            "список команд: -help");
            }

        }
    }

    private static void showListOfCommands() {
        System.out.printf("\n***Вы в главном меню***\n\n" +
                "Команды для работы с хранилищем данных:\n" +
                "%-19s 1\n" +
                "%-19s 2\n" +
                "%-19s 3\n" +
                "%-18s -help\n" +
                "%-18s -exit\n", "writers.json:", "posts.json:", "labels.json:", "список команд:", "завершить работу:");
    }
}


