package me.tomassetti.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Comment {
    UUID commentUuid;
    UUID postUuid;
    String author;
    String text;
    boolean approved;
    Date submissionDate;
}
