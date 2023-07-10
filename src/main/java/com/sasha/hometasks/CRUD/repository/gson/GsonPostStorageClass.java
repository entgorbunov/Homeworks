package com.sasha.hometasks.CRUD.repository.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sasha.hometasks.CRUD.model.Post;
import com.sasha.hometasks.CRUD.model.PostStatus;
import com.sasha.hometasks.CRUD.repository.PostStorage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class GsonPostStorageClass implements PostStorage {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String POSTS_FILE_PATH = "src/main/java/com/sasha/hometasks/CRUD/posts.json";
    private AtomicInteger nextId = new AtomicInteger(1);

    private List<Post> getAllInternal() {
        List<Post> allPosts;
        try (FileReader reader = new FileReader(POSTS_FILE_PATH)) {
            Type targetClassType = new TypeToken<ArrayList<Post>>() {
            }.getType();
            allPosts = GSON.fromJson(reader, targetClassType);
            if (allPosts == null)
                allPosts = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allPosts;
    }

    private void writeToFile(List<Post> allPosts) {
        try (FileWriter writer = new FileWriter(POSTS_FILE_PATH)) {
            writer.write(GSON.toJson(allPosts));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getNextId() {
        return nextId.getAndIncrement();
    }

    @Override
    public Post save(Post post) {
        post.setId(getNextId());
        List<Post> allPosts = getAllInternal();
        try (FileWriter writer = new FileWriter(POSTS_FILE_PATH)) {
            allPosts.add(post);
            writer.write(GSON.toJson(allPosts));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return post;
    }


    @Override
    public Post update(Post post) {
        List<Post> allPosts = getAllInternal();
        Optional<Post> optionalPost = allPosts.stream()
                .filter(p -> p.getId().equals(post.getId()) && p.getStatus() == PostStatus.ACTIVE)
                .findFirst();
        optionalPost.ifPresent(p -> {
            p.setContent(post.getContent());
            p.setCreated(post.getCreated());
            p.setUpdated(post.getUpdated());
            p.setLabels(post.getLabels());
            writeToFile(allPosts);
        });

        return post;
    }


    @Override
    public List<Post> getAll() {
        return getAllInternal();
    }

    @Override
    public Post getById(Integer id) {
        List<Post> allPosts = getAllInternal();
        return allPosts.stream()
                .filter(post -> post.getId().equals(id) && post.getStatus() == PostStatus.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Post> allPosts = getAllInternal();
        Optional<Post> optionalPost = allPosts.stream()
                .filter(post -> post.getId().equals(id) && post.getStatus() == PostStatus.ACTIVE)
                .findFirst();
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setStatus(PostStatus.DELETED);
            allPosts.remove(post);
            writeToFile(allPosts);
            return true;
        }
        return false;
    }
}
