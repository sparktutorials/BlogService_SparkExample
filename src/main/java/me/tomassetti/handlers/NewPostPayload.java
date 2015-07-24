package me.tomassetti.handlers;

import lombok.Data;
import me.tomassetti.Validable;

import java.util.LinkedList;
import java.util.List;

/**
* Created by federico on 24/07/15.
*/
@Data
class NewPostPayload implements Validable {
    private String title;
    private List<String> categories = new LinkedList<>();
    private String content;

    public boolean isValid() {
        return title != null && !title.isEmpty() && content != null && !content.isEmpty();
    }
}
