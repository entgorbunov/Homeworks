package com.sasha.hometasks.CRUD.controller;

import com.sasha.hometasks.CRUD.model.Post;
import com.sasha.hometasks.CRUD.repository.PostStorage;
import com.sasha.hometasks.CRUD.repository.gson.GsonPostStorageClass;

import java.util.List;

public class PostController {
    private static final PostStorage postStorage = new GsonPostStorageClass();

    public Post save(String content) {
        if (content.isEmpty() || content.isBlank()) {
            return null;
        }

        Post post = new Post();
        post.setContent(content);
        return postStorage.save(post);
    }

    public Post getById(Integer id) {
        if (id <= 0) {
            return null;
        }

        return postStorage.getById(id);
    }

    public static List<Post> getAll() {
        return postStorage.getAll();
    }

    public Post update(Post post) {
        if (post == null || post.getId() <= 0) {
            return null;
        }

        return postStorage.update(post);
    }

    public boolean deleteById(Integer id) {
        if (id <= 0) {
            return false;
        }

        return postStorage.deleteById(id);
    }
}
