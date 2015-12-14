package me.tomassetti.handlers;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.UUID;

import me.tomassetti.Answer;
import me.tomassetti.model.Model;

import org.easymock.EasyMock;
import org.junit.Test;


public class PostsCreateHandlerTest {

    @Test
    public void anInvalidNewPostReturnsBadRequest() {
        NewPostPayload newPost = new NewPostPayload();
        newPost.setTitle(""); // this makes the post invalid
        newPost.setContent("Bla bla bla");
        assertFalse(newPost.isValid());

        Model model = EasyMock.createMock(Model.class);
        replay(model);

        PostsCreateHandler handler = new PostsCreateHandler(model);
        assertEquals(new Answer(400), handler.process(newPost, Collections.emptyMap(), false));
        assertEquals(new Answer(400), handler.process(newPost, Collections.emptyMap(), true));

        verify(model);
    }

    @Test
    public void aPostIsCorrectlyCreated() {
        NewPostPayload newPost = new NewPostPayload();
        newPost.setTitle("My new post");
        newPost.setContent("Bla bla bla");
        assertTrue(newPost.isValid());

        Model model = EasyMock.createMock(Model.class);
        expect(model.createPost("My new post", "Bla bla bla", Collections.emptyList())).andReturn(UUID.fromString("728084e8-7c9a-4133-a9a7-f2bb491ef436"));
        replay(model);

        PostsCreateHandler handler = new PostsCreateHandler(model);
        assertEquals(new Answer(201, "728084e8-7c9a-4133-a9a7-f2bb491ef436"), handler.process(newPost, Collections.emptyMap(), false));

        verify(model);
    }

}
