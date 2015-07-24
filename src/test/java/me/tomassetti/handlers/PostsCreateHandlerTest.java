package me.tomassetti.handlers;

import me.tomassetti.Answer;
import me.tomassetti.model.Model;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;



public class PostsCreateHandlerTest {

    @Test
    public void anInvalidNewPostReturnsBadRequest() {
        NewPostPayload newPost = new NewPostPayload();
        newPost.setTitle(""); // this makes the post invalid
        newPost.setContent("Bla bla bla");
        assertFalse(newPost.isValid());

        Model model = EasyMock.createMock(Model.class);

        PostsCreateHandler handler = new PostsCreateHandler(model);
        assertEquals(new Answer(400), handler.process(newPost, Collections.emptyMap(), false));
        assertEquals(new Answer(400), handler.process(newPost, Collections.emptyMap(), true));
    }

}
