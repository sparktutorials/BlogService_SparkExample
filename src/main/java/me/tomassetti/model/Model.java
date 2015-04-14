package me.tomassetti.model;

import java.util.UUID;

public interface Model {
    
    UUID createPost(String title, String content);
    UUID createComment(UUID post, String author, String content);
    
}
