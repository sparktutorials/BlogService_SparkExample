package me.tomassetti;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.tomassetti.model.Model;

import java.util.Map;
import java.util.UUID;

/**
 * Created by federico on 22/07/15.
 */
public class PostsCreateHandler extends AbstractRequestHandler<BlogService.NewPostPayload> {

    private Model model;

    public PostsCreateHandler(Model model) {
        super(BlogService.NewPostPayload.class);
        this.model = model;
    }

    @Override
    public Answer process(BlogService.NewPostPayload value, Map<String, String> queryParams) {
        UUID id = model.createPost(value.getTitle(), value.getContent(), value.getCategories());
        return new Answer(200, id.toString());
    }

}
