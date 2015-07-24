package me.tomassetti.handlers;

import me.tomassetti.AbstractRequestHandler;
import me.tomassetti.Answer;
import me.tomassetti.Validable;
import me.tomassetti.model.Model;

import java.util.Map;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;

/**
 * Created by federico on 22/07/15.
 */
public class PostsIndexHandler extends AbstractRequestHandler<EmptyPayload> {

    public PostsIndexHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    public Answer process(EmptyPayload value, Map queryParams, boolean shouldReturnHtml) {
        if (shouldReturnHtml) {
            String html = body().with(
                    h1("My wonderful blog"),
                    div().with(
                            model.getAllPosts().stream().map((p) ->
                                    div().with(
                                            h2(p.getTitle()),
                                            p(p.getContent()),
                                            ul().with(p.getCategories().stream().map((cat) ->
                                                    li(cat))
                                                    .collect(Collectors.toList()))))
                                    .collect(Collectors.toList()))
            ).render();
            return Answer.ok(html);
        } else {
            String json = dataToJson(model.getAllPosts());
            return Answer.ok(json);
        }
    }

}
