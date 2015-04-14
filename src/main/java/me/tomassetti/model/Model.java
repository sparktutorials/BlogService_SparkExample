package me.tomassetti.model;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface Model {
    UUID createPost(String title, String content, List<String> categories);
    UUID createComment(UUID post, String author, String content);
    List<Post> getAllPosts();
}
