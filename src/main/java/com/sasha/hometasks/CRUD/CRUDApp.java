package com.sasha.hometasks.CRUD;

import com.google.gson.Gson;
import com.sasha.hometasks.CRUD.model.Label;
import com.sasha.hometasks.CRUD.model.Post;
import com.sasha.hometasks.CRUD.model.Writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CRUDApp {

    private static final List<Writer> writers = new ArrayList<>();
    private static int writerIdCounter = 1;
    private static int postIdCounter = 1;
    private static int labelIdCounter = 1;

    public static void main(String[] args) {
        Gson gson = new Gson();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("=== com.sasha.hometasks.CRUD Application ===");
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Exit");
            System.out.print("Сделайте выбор: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1 -> createEntity(scanner);
                case 2 -> readEntity(scanner);
                case 3 -> updateEntity(scanner);
                case 4 -> deleteEntity(scanner);
                case 5 -> exit = true;
                default -> System.out.println("Неправильный выбор. Выберите из списка.");
            }

            System.out.println();
        }
    }

    private static void createEntity(Scanner scanner) {
        System.out.println("=== Create Entity ===");
        System.out.println("1. Create Writer");
        System.out.println("2. Create Post");
        System.out.println("3. Create Label");
        System.out.print("Сделайте выбор: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> createWriter(scanner);
            case 2 -> createPost(scanner);
            case 3 -> createLabel(scanner);
            default -> System.out.println("Неправильный выбор. Выберите из списка.");
        }
    }

    private static void createWriter(Scanner scanner) {
        System.out.println("=== Create Writer ===");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        Writer writer = new Writer(writerIdCounter++, firstName, lastName, new ArrayList<>());
        writers.add(writer);

        System.out.println("Writer created successfully.");
    }

    private static void createPost(Scanner scanner) {
        System.out.println("=== Create Post ===");
        System.out.print("Enter writer ID: ");
        int writerId = scanner.nextInt();
        scanner.nextLine();
        Writer writer = getWriterById(writerId);
        if (writer == null) {
            System.out.println("Writer not found.");
            return;
        }

        System.out.print("Enter post content: ");
        String content = scanner.nextLine();

        Post post = new Post();
        post.setId(postIdCounter++);
        post.setContent(content);
        post.setCreated(new Date());
        post.setUpdated(new Date());
        post.setLabels(new ArrayList<>());

        writer.getPosts().add(post);

        System.out.println("Post created successfully.");
    }

    private static void createLabel(Scanner scanner) {
        System.out.println("=== Create Label ===");
        System.out.print("Enter post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Post post = getPostById(postId);
        if (post == null) {
            System.out.println("Post not found.");
            return;
        }

        System.out.print("Enter label name: ");
        String name = scanner.nextLine();

        Label label = new Label();
        label.setId(labelIdCounter++);
        label.setName(name);

        post.getLabels().add(label);

        System.out.println("Label created successfully.");
    }

    private static void readEntity(Scanner scanner) {
        System.out.println("=== Read Entity ===");
        System.out.println("1. Read Writer");
        System.out.println("2. Read Post");
        System.out.println("3. Read Label");
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> readWriter(scanner);
            case 2 -> readPost(scanner);
            case 3 -> readLabel(scanner);
            default -> System.out.println("Invalid option. Please try again.");
        }
    }

    private static void readWriter(Scanner scanner) {
        System.out.println("=== Read Writer ===");
        System.out.print("Enter writer ID: ");
        int writerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Writer writer = getWriterById(writerId);
        if (writer == null) {
            System.out.println("Writer not found.");
            return;
        }

        System.out.println(writer);
    }

    private static void readPost(Scanner scanner) {
        System.out.println("=== Read Post ===");
        System.out.print("Enter post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Post post = getPostById(postId);
        if (post == null) {
            System.out.println("Post not found.");
            return;
        }

        System.out.println(post);
    }

    private static void readLabel(Scanner scanner) {
        System.out.println("=== Read Label ===");
        System.out.print("Enter label ID: ");
        int labelId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Label label = getLabelById(labelId);
        if (label == null) {
            System.out.println("Label not found.");
            return;
        }

        System.out.println(label);
    }

    private static void updateEntity(Scanner scanner) {
        System.out.println("=== Update Entity ===");
        System.out.println("1. Update Writer");
        System.out.println("2. Update Post");
        System.out.println("3. Update Label");
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (option) {
            case 1 -> updateWriter(scanner);
            case 2 -> updatePost(scanner);
            case 3 -> updateLabel(scanner);
            default -> System.out.println("Invalid option. Please try again.");
        }
    }

    private static void updateWriter(Scanner scanner) {
        System.out.println("=== Update Writer ===");
        System.out.print("Enter writer ID: ");
        int writerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Writer writer = getWriterById(writerId);
        if (writer == null) {
            System.out.println("Writer not found.");
            return;
        }

        System.out.print("Enter new first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter new last name: ");
        String lastName = scanner.nextLine();

        writer.setFirstName(firstName);
        writer.setLastName(lastName);

        System.out.println("Writer updated successfully.");
    }

    private static void updatePost(Scanner scanner) {
        System.out.println("=== Update Post ===");
        System.out.print("Enter post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Post post = getPostById(postId);
        if (post == null) {
            System.out.println("Post not found.");
            return;
        }

        System.out.print("Enter new content: ");
        String content = scanner.nextLine();
        post.setContent(content);
        post.setUpdated(new Date());

        System.out.println("Post updated successfully.");
    }

    private static void updateLabel(Scanner scanner) {
        System.out.println("=== Update Label ===");
        System.out.print("Enter label ID: ");
        int labelId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Label label = getLabelById(labelId);
        if (label == null) {
            System.out.println("Label not found.");
            return;
        }

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        label.setName(name);

        System.out.println("Label updated successfully.");
    }

    private static void deleteEntity(Scanner scanner) {
        System.out.println("=== Delete Entity ===");
        System.out.println("1. Delete Writer");
        System.out.println("2. Delete Post");
        System.out.println("3. Delete Label");
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> deleteWriter(scanner);
            case 2 -> deletePost(scanner);
            case 3 -> deleteLabel(scanner);
            default -> System.out.println("Invalid option. Please try again.");
        }
    }

    private static void deleteWriter(Scanner scanner) {
        System.out.println("=== Delete Writer ===");
        System.out.print("Enter writer ID: ");
        int writerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Writer writer = getWriterById(writerId);
        if (writer == null) {
            System.out.println("Writer not found.");
            return;
        }

        // Set writer status to DELETED
        writer.setStatus(PostStatus.DELETED.toString());

        System.out.println("Writer deleted successfully.");
    }

    private static void deletePost(Scanner scanner) {
        System.out.println("=== Delete Post ===");
        System.out.print("Enter post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Post post = getPostById(postId);
        if (post == null) {
            System.out.println("Post not found.");
            return;
        }

        // Set post status to DELETED
        post.setStatus(PostStatus.DELETED.toString());

        System.out.println("Post deleted successfully.");
    }

    private static void deleteLabel(Scanner scanner) {
        System.out.println("=== Delete Label ===");
        System.out.print("Enter label ID: ");
        int labelId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Label label = getLabelById(labelId);
        if (label == null) {
            System.out.println("Label not found.");
            return;
        }

        // Set label status to DELETED
        label.setStatus(PostStatus.DELETED.toString());

        System.out.println("Label deleted successfully.");
    }

    private static Writer getWriterById(int writerId) {
        for (Writer writer : writers) {
            if (writer.getId() == writerId) {
                return writer;
            }
        }
        return null;
    }

    private static Post getPostById(int postId) {
        for (Writer writer : writers) {
            for (Post post : writer.getPosts()) {
                if (post.getId() == postId) {
                    return post;
                }
            }
        }
        return null;
    }

    private static Label getLabelById(int labelId) {
        for (Writer writer : writers) {
            for (Post post : writer.getPosts()) {
                for (Label label : post.getLabels()) {
                    if (label.getId() == labelId) {
                        return label;
                    }
                }
            }
        }
        return null;
    }
}
