package me.tomassetti.handlers;

import me.tomassetti.AbstractRequestHandler;
import me.tomassetti.Answer;
import me.tomassetti.model.Model;

import java.util.Map;
import java.util.UUID;

public class PostsCreateHandler extends AbstractRequestHandler<NewPostPayload> {

    private Model model;

    public PostsCreateHandler(Model model) {
        super(NewPostPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(NewPostPayload value, Map<String, String> urlParams, boolean shouldReturnHtml) {
        UUID id = model.createPost(value.getTitle(), value.getContent(), value.getCategories());
        return new Answer(200, id.toString());
    }
}
