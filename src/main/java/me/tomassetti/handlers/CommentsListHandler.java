package me.tomassetti.handlers;

import me.tomassetti.AbstractRequestHandler;
import me.tomassetti.Answer;
import me.tomassetti.model.Model;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;

public class CommentsListHandler extends AbstractRequestHandler<EmptyPayload> {

    public CommentsListHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map<String, String> urlParams, boolean shouldReturnHtml) {
        UUID post = UUID.fromString(urlParams.get(":uuid"));
        if (!model.existPost(post)) {
            return new Answer(400);
        }
        if (shouldReturnHtml) {
            String html = body().with(
                    h1("Comments for post " + post),
                    div().with(
                            model.getAllCommentsOn(post).stream().map((comment) ->
                                    div().with(
                                            h2(comment.getAuthor()),
                                            p(comment.getContent()))
                                    ).collect(Collectors.toList()))
            ).render();
            return Answer.ok(html);
        } else {
            String json = dataToJson(model.getAllCommentsOn(post));
            return Answer.ok(json);
        }
    }

}
