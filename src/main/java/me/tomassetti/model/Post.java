package me.tomassetti.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Post {
    private UUID postUuid;
    private String title;
    private String content;
    private Date publishingDate;
    private List<String> categories;
}
