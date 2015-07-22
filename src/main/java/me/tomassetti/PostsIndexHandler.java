package me.tomassetti;

import java.util.Map;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;

/**
 * Created by federico on 22/07/15.
 */
public class PostsIndexHandler extends AbstractRequestHandler {

    @Override
    public Answer process(Validable value, Map queryParams) {
        if (shouldReturnHtml(request)) {
            response.status(200);
            response.type("text/html");
            return body().with(
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
        } else {
            response.status(200);
            response.type("application/json");
            return dataToJson(model.getAllPosts());
        }
    }

}
