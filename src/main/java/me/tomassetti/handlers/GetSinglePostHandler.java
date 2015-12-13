package me.tomassetti.handlers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import me.tomassetti.AbstractRequestHandler;
import me.tomassetti.Answer;
import me.tomassetti.model.Model;
import me.tomassetti.model.Post;

public class GetSinglePostHandler extends AbstractRequestHandler<EmptyPayload> {

    public GetSinglePostHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map<String,String> urlParams, boolean shouldReturnHtml) {
        if (!urlParams.containsKey(":uuid")) {
            throw new IllegalArgumentException();
        }
        UUID uuid;
        try {
            uuid = UUID.fromString(urlParams.get(":uuid"));
        } catch (IllegalArgumentException e) {
            return new Answer(404);
        }

        Optional<Post> post = model.getPost(uuid);
        if (!post.isPresent()) {
            return new Answer(404);
        }
        return Answer.ok(dataToJson(post.get()));
    }

}
