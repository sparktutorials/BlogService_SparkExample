CREATE TABLE posts (
    post_uuid uuid primary key,
    title text not null,
    content text,
    publishing_date date
);

CREATE TABLE comments (
    comment_uuid uuid primary key,
    post_uuid uuid,
    author text,
    content text,
    approved bool,
    submission_date date
);

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO blog_owner;
