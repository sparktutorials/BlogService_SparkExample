package me.tomassetti.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Comment {
    UUID comment_uuid;
    UUID postUuid;
    String author;
    String text;
    boolean approved;
    Date submission_date;
}
