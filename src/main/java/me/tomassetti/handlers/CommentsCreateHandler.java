package me.tomassetti.handlers;

import me.tomassetti.AbstractRequestHandler;
import me.tomassetti.Answer;
import me.tomassetti.model.Model;

import java.util.Map;
import java.util.UUID;

public class CommentsCreateHandler extends AbstractRequestHandler<NewCommentPayload> {

    private Model model;

    public CommentsCreateHandler(Model model) {
        super(NewCommentPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(NewCommentPayload creation, Map<String, String> queryParams, boolean shouldReturnHtml) {
        UUID post = UUID.fromString(queryParams.get(":uuid"));
        if (!model.existPost(post)){
            return new Answer(400);
        }
        UUID id = model.createComment(post, creation.getAuthor(), creation.getContent());
        return Answer.ok(id.toString());
    }
}
