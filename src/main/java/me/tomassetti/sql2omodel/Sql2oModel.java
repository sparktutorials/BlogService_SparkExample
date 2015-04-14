package me.tomassetti.sql2omodel;

import me.tomassetti.RandomUuidGenerator;
import me.tomassetti.UuidGenerator;
import me.tomassetti.model.Model;
import me.tomassetti.model.Post;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Sql2oModel implements Model {

    private Sql2o sql2o;
    private UuidGenerator uuidGenerator;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
        uuidGenerator = new RandomUuidGenerator();
    }

    @Override
    public UUID createPost(String title, String content, List<String> categories) {
        try (Connection conn = sql2o.open()) {
            UUID postUuid = uuidGenerator.generate();
            // Create the country brand
            conn.createQuery("insert into posts(post_uuid, title, content, publishing_date) VALUES (:post_uuid, :title, :content, :date)")
                    .addParameter("post_uuid", postUuid)
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("date", new Date())
                    .executeUpdate();
            return postUuid;
        }
    }

    @Override
    public UUID createComment(UUID post, String author, String content) {
        return null;
    }

    @Override
    public List<Post> getAllPosts() {
        return null;
    }

}
