package me.tomassetti.handlers;

import me.tomassetti.AbstractRequestHandler;
import me.tomassetti.Answer;
import me.tomassetti.model.Model;
import me.tomassetti.model.Post;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PostsEditHandler extends AbstractRequestHandler<EditPostPayload> {

    private Model model;

    public PostsEditHandler(Model model) {
        super(EditPostPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(EditPostPayload value, Map<String, String> urlParams, boolean shouldReturnHtml) {
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
        if (value.getTitle() != null) {
            post.get().setTitle(value.getTitle());
        }
        if (value.getContent() != null) {
            post.get().setContent(value.getContent());
        }
        if (value.getCategories() != null) {
            post.get().setCategories(value.getCategories());
        }
        model.updatePost(post.get());
        return new Answer(200);
    }
}
