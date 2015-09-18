package me.tomassetti.sql2omodel;

import me.tomassetti.RandomUuidGenerator;
import me.tomassetti.UuidGenerator;
import me.tomassetti.model.Comment;
import me.tomassetti.model.Model;
import me.tomassetti.model.Post;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        try (Connection conn = sql2o.beginTransaction()) {
            UUID postUuid = uuidGenerator.generate();
            conn.createQuery("insert into posts(post_uuid, title, content, publishing_date) VALUES (:post_uuid, :title, :content, :date)")
                    .addParameter("post_uuid", postUuid)
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("date", new Date())
                    .executeUpdate();
            categories.forEach((category) ->
                    conn.createQuery("insert into posts_categories(post_uuid, category) VALUES (:post_uuid, :category)")
                    .addParameter("post_uuid", postUuid)
                    .addParameter("category", category)
                    .executeUpdate());
            conn.commit();
            return postUuid;
        }
    }

    @Override
    public UUID createComment(UUID post, String author, String content) {
        try (Connection conn = sql2o.open()) {
            UUID commentUuid = uuidGenerator.generate();
            conn.createQuery("insert into comments(comment_uuid, post_uuid, author, content, approved, submission_date) VALUES (:comment_uuid, :post_uuid, :author, :content, :approved, :date)")
                    .addParameter("comment_uuid", commentUuid)
                    .addParameter("post_uuid", post)
                    .addParameter("author", author)
                    .addParameter("content", content)
                    .addParameter("approved", false)
                    .addParameter("date", new Date())
                    .executeUpdate();
            return commentUuid;
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try (Connection conn = sql2o.open()) {
            List<Post> posts = conn.createQuery("select * from posts")
                    .executeAndFetch(Post.class);
            posts.forEach((post) -> post.setCategories(getCategoriesFor(conn, post.getPost_uuid())));
            return posts;
        }
    }

    private List<String> getCategoriesFor(Connection conn, UUID post_uuid) {
        return conn.createQuery("select category from posts_categories where post_uuid=:post_uuid")
                .addParameter("post_uuid", post_uuid)
                .executeAndFetch(String.class);
    }

    @Override
    public List<Comment> getAllCommentsOn(UUID post) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select * from comments where post_uuid=:post_uuid")
                    .addParameter("post_uuid", post)
                    .executeAndFetch(Comment.class);
        }
    }

    @Override
    public boolean existPost(UUID post) {
        try (Connection conn = sql2o.open()) {
            List<Post> posts = conn.createQuery("select * from posts where post_uuid=:post")
                    .addParameter("post", post)
                    .executeAndFetch(Post.class);
            return posts.size() > 0;
        }
    }

    @Override
    public Optional<Post> getPost(UUID uuid) {
        try (Connection conn = sql2o.open()) {
            List<Post> posts = conn.createQuery("select * from posts where post_uuid=:post_uuid")
                    .addParameter("post_uuid", uuid)
                    .executeAndFetch(Post.class);
            if (posts.size() == 0) {
                return Optional.empty();
            } else if (posts.size() == 1) {
                return Optional.of(posts.get(0));
            } else {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public void updatePost(Post post) {
        try (Connection conn = sql2o.open()) {
            conn.createQuery("update posts set title=:title, content=:content where post_uuid=:post_uuid")
                    .addParameter("post_uuid", post.getPost_uuid())
                    .addParameter("title", post.getTitle())
                    .addParameter("content", post.getContent())
                    .executeUpdate();
        }
    }

    @Override
    public void deletePost(UUID uuid) {
        try (Connection conn = sql2o.open()) {
            conn.createQuery("delete from posts where post_uuid=:post_uuid")
                    .addParameter("post_uuid", uuid)
                    .executeUpdate();
        }
    }

}
