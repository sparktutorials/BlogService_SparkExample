package me.tomassetti.handlers;

import lombok.Data;
import me.tomassetti.Validable;

/**
* Created by federico on 24/07/15.
*/
@Data
class NewCommentPayload implements Validable {
    private String author;
    private String content;

    public boolean isValid() {
        return author != null && !author.isEmpty() && content != null && !content.isEmpty();
    }
}
