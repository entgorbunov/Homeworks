package com.sasha.hometasks.CRUD.controller;

import com.sasha.hometasks.CRUD.model.Post;
import com.sasha.hometasks.CRUD.repository.PostRepository;
import com.sasha.hometasks.CRUD.repository.gson.GsonPostRepositoryImpl;

import java.util.List;

public class PostController {
    private static final PostRepository postRepository = new GsonPostRepositoryImpl();

    public Post save(String content) {
        if (content.isEmpty() || content.isBlank()) {
            return null;
        }

        Post post = new Post();
        post.setContent(content);
        return postRepository.save(post);
    }

    public Post getById(Integer id) {
        if (id <= 0) {
            return null;
        }

        return postRepository.getById(id);
    }

    public static List<Post> getAll() {
        return postRepository.getAll();
    }

    public Post update(Post post) {
        if (post == null || post.getId() <= 0) {
            return null;
        }

        return postRepository.update(post);
    }

    public boolean deleteById(Integer id) {
        if (id <= 0) {
            return false;
        }

        return postRepository.deleteById(id);
    }
}
