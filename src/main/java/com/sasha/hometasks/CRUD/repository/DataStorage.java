package com.sasha.hometasks.CRUD.repository;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sasha.hometasks.CRUD.model.Label;
import com.sasha.hometasks.CRUD.model.Post;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static final String WRITERS_FILE = "writers.json";
    private static final String POSTS_FILE = "posts.json";
    private static final String LABELS_FILE = "labels.json";
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveWriters(List<Writer> writers) {
        try (Writer writer = new FileWriter(WRITERS_FILE)) {
            gson.toJson(writers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Writer> loadWriters() {
        List<Writer> writers = new ArrayList<>();
        try (Reader reader = new FileReader(WRITERS_FILE)) {
            Type type = new TypeToken<List<Writer>>() {}.getType();
            writers = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writers;
    }

    public static void savePosts(List<Post> posts) {
        try (Writer writer = new FileWriter(POSTS_FILE)) {
            gson.toJson(posts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Post> loadPosts() {
        List<Post> posts = new ArrayList<>();
        try (Reader reader = new FileReader(POSTS_FILE)) {
            Type type = new TypeToken<List<Post>>() {}.getType();
            posts = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static void saveLabels(List<Label> labels) {
        try (Writer writer = new FileWriter(LABELS_FILE)) {
            gson.toJson(labels, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Label> loadLabels() {
        List<Label> labels = new ArrayList<>();
        try (Reader reader = new FileReader(LABELS_FILE)) {
            Type type = new TypeToken<List<Label>>() {}.getType();
            labels = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return labels;
    }
}

